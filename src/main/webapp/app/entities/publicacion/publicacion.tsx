import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Col, Row, Card, CardTitle, CardText } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './publicacion.reducer';
import { IPublicacion } from 'app/shared/model/publicacion.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

import './publicacion.scss';

export interface IPublicacionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> { }

export const Publicacion = (props: IPublicacionProps) => {
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

  const { publicacionList, match, loading } = props;
  return (
    <div id="div-publicacion">
      <h2 id="publicacion-heading">
        <Translate contentKey="reactApp.publicacion.home.title">Publicacions</Translate>
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
          {publicacionList && publicacionList.length > 0 ? (
            <Row>
              <Col md="12">
                <div className="publicaciones">
                  {publicacionList.map((publicacion, i) => (
                    <Card body className="my-4 mx-1" key={`entity-${i}`}>
                      <Link to={`${match.url}/${publicacion.id}`}>
                        <Row>
                          <Col>
                            <CardText>{publicacion.date ? <TextFormat type="date" value={publicacion.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</CardText>
                            <Row className="publicacion-item">
                              <Col>{publicacion.title}</Col>
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
                  <Translate contentKey="reactApp.publicacion.home.notFound">No Publicacions found</Translate>
                </div>
              )
            )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

const mapStateToProps = ({ publicacion }: IRootState) => ({
  publicacionList: publicacion.entities,
  loading: publicacion.loading,
  totalItems: publicacion.totalItems,
  links: publicacion.links,
  entity: publicacion.entity,
  updateSuccess: publicacion.updateSuccess,
});

const mapDispatchToProps = {
  getEntities,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Publicacion);
