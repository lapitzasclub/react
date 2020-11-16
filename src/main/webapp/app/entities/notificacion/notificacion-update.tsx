import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './notificacion.reducer';
import { INotificacion } from 'app/shared/model/notificacion.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INotificacionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const NotificacionUpdate = (props: INotificacionUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { notificacionEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/notificacion');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...notificacionEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reactApp.notificacion.home.createOrEditLabel">
            <Translate contentKey="reactApp.notificacion.home.createOrEditLabel">Create or edit a Notificacion</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : notificacionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="notificacion-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="notificacion-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateLabel" for="notificacion-date">
                  <Translate contentKey="reactApp.notificacion.date">Date</Translate>
                </Label>
                <AvField
                  id="notificacion-date"
                  type="date"
                  className="form-control"
                  name="date"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isReadLabel">
                  <AvInput id="notificacion-isRead" type="checkbox" className="form-check-input" name="isRead" />
                  <Translate contentKey="reactApp.notificacion.isRead">Is Read</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="titleLabel" for="notificacion-title">
                  <Translate contentKey="reactApp.notificacion.title">Title</Translate>
                </Label>
                <AvField
                  id="notificacion-title"
                  type="text"
                  name="title"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 50, errorMessage: translate('entity.validation.maxlength', { max: 50 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="summaryLabel" for="notificacion-summary">
                  <Translate contentKey="reactApp.notificacion.summary">Summary</Translate>
                </Label>
                <AvField
                  id="notificacion-summary"
                  type="text"
                  name="summary"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 100, errorMessage: translate('entity.validation.maxlength', { max: 100 }) },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/notificacion" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  notificacionEntity: storeState.notificacion.entity,
  loading: storeState.notificacion.loading,
  updating: storeState.notificacion.updating,
  updateSuccess: storeState.notificacion.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(NotificacionUpdate);
