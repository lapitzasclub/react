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

export interface IPublicacionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export const PublicacionDetail = (props: IPublicacionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { publicacionEntity } = props;
  return (
    <Row>
      <Col xs-12 className="mb-3">
        <Button tag={Link} to="/publicacion" replace outline color="primary" className="float-right close">
          <FontAwesomeIcon icon="times" size="lg"/>{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
      </Col>
      <Col md="8">
        <h2>
          {publicacionEntity.title}
        </h2>
        <div className="date">
          {publicacionEntity.date ? <TextFormat type="date" value={publicacionEntity.date} format={APP_LOCAL_DATE_FORMAT} /> : null}
        </div>
        <div className="summary">{publicacionEntity.summary}</div>
        <div>{publicacionEntity.content}</div>
      </Col>
    </Row >
  );
};

const mapStateToProps = ({ publicacion }: IRootState) => ({
  publicacionEntity: publicacion.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PublicacionDetail);
