import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './notificacion.reducer';
import { INotificacion } from 'app/shared/model/notificacion.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INotificacionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const NotificacionDetail = (props: INotificacionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { notificacionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="reactApp.notificacion.detail.title">Notificacion</Translate> [<b>{notificacionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="date">
              <Translate contentKey="reactApp.notificacion.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {notificacionEntity.date ? <TextFormat value={notificacionEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="isRead">
              <Translate contentKey="reactApp.notificacion.isRead">Is Read</Translate>
            </span>
          </dt>
          <dd>{notificacionEntity.isRead ? 'true' : 'false'}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="reactApp.notificacion.title">Title</Translate>
            </span>
          </dt>
          <dd>{notificacionEntity.title}</dd>
          <dt>
            <span id="summary">
              <Translate contentKey="reactApp.notificacion.summary">Summary</Translate>
            </span>
          </dt>
          <dd>{notificacionEntity.summary}</dd>
        </dl>
        <Button tag={Link} to="/notificacion" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notificacion/${notificacionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ notificacion }: IRootState) => ({
  notificacionEntity: notificacion.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(NotificacionDetail);
