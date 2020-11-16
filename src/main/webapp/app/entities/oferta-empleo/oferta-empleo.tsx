import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Col, Row, Card, CardTitle } from 'reactstrap';
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './oferta-empleo.reducer';
import { IOfertaEmpleo } from 'app/shared/model/oferta-empleo.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

import './oferta-empleo.scss';

export interface IOfertaEmpleoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> { }

export const OfertaEmpleo = (props: IOfertaEmpleoProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const resetAll = () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    props.getEntities();
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      resetAll();
    }
  }, [props.updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
    setSorting(true);
  };

  const { ofertaEmpleoList, match, loading } = props;
  return (
    <div id="div-oferta-empleo">
      <h2 id="oferta-empleo-heading">
        <Translate contentKey="reactApp.ofertaEmpleo.home.title">Oferta Empleos</Translate>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < props.links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {ofertaEmpleoList && ofertaEmpleoList.length > 0 ? (
            <Row>
              <Col md="12">
                <div className="ofertas">
                  {ofertaEmpleoList.map((ofertaEmpleo, i) => (
                    <Card body className="my-4 mx-1" key={`entity-${i}`}>
                      <Link to={`${match.url}/${ofertaEmpleo.id}`}>
                        <Row>
                          <Col>
                            <CardTitle tag="h5" className="text-uppercase font-weight-bold">
                              {ofertaEmpleo.title}
                            </CardTitle>
                            <Row className="oferta-item">
                              <Col xs="1" className="icon">
                                <FontAwesomeIcon icon="map-marker-alt" />
                              </Col>
                              <Col><strong>{ofertaEmpleo.place}</strong></Col>
                            </Row>
                            <Row className="oferta-item">
                              <Col xs="1" className="icon">
                                <FontAwesomeIcon icon="user-friends" />
                              </Col>
                              <Col><Translate contentKey="reactApp.ofertaEmpleo.slots" />: {ofertaEmpleo.slots}</Col>
                            </Row>
                            <Row className="oferta-item">
                              <Col xs="1" className="icon">
                                <FontAwesomeIcon icon="briefcase" />
                              </Col>
                              <Col><Translate contentKey="reactApp.ofertaEmpleo.contract" />: {ofertaEmpleo.contract}</Col>
                            </Row>
                            <Row className="oferta-item">
                              <Col xs="1" className="icon">
                                <FontAwesomeIcon icon="calendar-alt" />
                              </Col>
                              <Col>{ofertaEmpleo.term}</Col>
                            </Row>
                          </Col>
                          <Col xs="1">
                            <FontAwesomeIcon icon="angle-right" size="lg" className="caret" />
                          </Col>
                        </Row>
                      </Link>
                    </Card>
                  ))}
                </div>
              </Col>
            </Row>
          ) : (
              !loading && (
                <div className="alert alert-warning">
                  <Translate contentKey="reactApp.ofertaEmpleo.home.notFound">No Oferta Empleos found</Translate>
                </div>
              )
            )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

const mapStateToProps = ({ ofertaEmpleo }: IRootState) => ({
  ofertaEmpleoList: ofertaEmpleo.entities,
  loading: ofertaEmpleo.loading,
  totalItems: ofertaEmpleo.totalItems,
  links: ofertaEmpleo.links,
  entity: ofertaEmpleo.entity,
  updateSuccess: ofertaEmpleo.updateSuccess,
});

const mapDispatchToProps = {
  getEntities,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OfertaEmpleo);
