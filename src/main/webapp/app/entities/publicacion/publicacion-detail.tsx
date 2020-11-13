import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './publicacion.reducer';
import { IPublicacion } from 'app/shared/model/publicacion.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPublicacionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PublicacionDetail = (props: IPublicacionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { publicacionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="reactApp.publicacion.detail.title">Publicacion</Translate> [<b>{publicacionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">
              <Translate contentKey="reactApp.publicacion.title">Title</Translate>
            </span>
          </dt>
          <dd>{publicacionEntity.title}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="reactApp.publicacion.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {publicacionEntity.date ? <TextFormat value={publicacionEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="summary">
              <Translate contentKey="reactApp.publicacion.summary">Summary</Translate>
            </span>
          </dt>
          <dd>{publicacionEntity.summary}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="reactApp.publicacion.content">Content</Translate>
            </span>
          </dt>
          <dd>{publicacionEntity.content}</dd>
        </dl>
        <Button tag={Link} to="/publicacion" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/publicacion/${publicacionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ publicacion }: IRootState) => ({
  publicacionEntity: publicacion.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PublicacionDetail);
