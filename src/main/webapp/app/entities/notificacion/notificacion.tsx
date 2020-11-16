import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Card, CardTitle, CardText } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './notificacion.reducer';
import { INotificacion } from 'app/shared/model/notificacion.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

import './notificacion.scss';

export interface INotificacionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> { }

export const Notificacion = (props: INotificacionProps) => {
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

  const { notificacionList, match, loading } = props;

  const notificacionListByDate: any = {};
  for (const notificacion of notificacionList) {
    if (!notificacionListByDate[notificacion.date]){
      notificacionListByDate[notificacion.date] = [];
    }
    notificacionListByDate[notificacion.date].push(notificacion);
  }

  return (
    <div id="div-notificacion">
      <h2 id="notificacion-heading">
        <Translate contentKey="reactApp.notificacion.home.title">Notificacions</Translate>
        <Link to={`${match.url}/new`} className="float-right mark-all-read" >
          <Translate contentKey="reactApp.notificacion.home.markAllRead">Marcar todas leídas</Translate>
        </Link>
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
          {notificacionList && notificacionList.length > 0 ? (
            <Row>
              <Col md="12">
                <div className="notificaciones">
                  {notificacionList.map((notificacion, i) => (
                    <Card body className={"my-4 mx-1 " + (notificacion.isRead ? "is-read" : "")} key={`entity-${i}`}>
                      <CardTitle tag="h5" className="text-uppercase font-weight-bold">
                        {notificacion.title}
                      </CardTitle>
                      <Row className="oferta-item">
                        <Col>{notificacion.summary}</Col>
                      </Row>
                      <Button tag={Link} to={`${match.url}/${notificacion.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </Card>
                  ))}
                </div>
              </Col>
            </Row>
          ) : (
              !loading && (
                <div className="alert alert-warning">
                  <Translate contentKey="reactApp.notificacion.home.notFound">No Notificacions found</Translate>
                </div>
              )
            )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

const mapStateToProps = ({ notificacion }: IRootState) => ({
  notificacionList: notificacion.entities,
  loading: notificacion.loading,
  totalItems: notificacion.totalItems,
  links: notificacion.links,
  entity: notificacion.entity,
  updateSuccess: notificacion.updateSuccess,
});

const mapDispatchToProps = {
  getEntities,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Notificacion);
