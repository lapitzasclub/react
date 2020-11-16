import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './oferta-empleo.reducer';
import { IOfertaEmpleo } from 'app/shared/model/oferta-empleo.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOfertaEmpleoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export const OfertaEmpleoDetail = (props: IOfertaEmpleoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { ofertaEmpleoEntity } = props;
  return (
    <Row>
      <Col xs-12 className="mb-3">
        <Button tag={Link} to="/oferta-empleo" replace outline color="primary" className="float-right close">
          <FontAwesomeIcon icon="times" size="lg"/>{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
      </Col>
      <Col md="8">
        <h2>
          {ofertaEmpleoEntity.title}
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="place">
              <Translate contentKey="reactApp.ofertaEmpleo.place">Place</Translate>
            </span>
          </dt>
          <dd>{ofertaEmpleoEntity.place}</dd>
          <dt>
            <span id="slots">
              <Translate contentKey="reactApp.ofertaEmpleo.slots">Slots</Translate>
            </span>
          </dt>
          <dd>{ofertaEmpleoEntity.slots}</dd>
          <dt>
            <span id="contract">
              <Translate contentKey="reactApp.ofertaEmpleo.contract">Contract</Translate>
            </span>
          </dt>
          <dd>{ofertaEmpleoEntity.contract}</dd>
          <dt>
            <span id="term">
              <Translate contentKey="reactApp.ofertaEmpleo.term">Term</Translate>
            </span>
          </dt>
          <dd>{ofertaEmpleoEntity.term}</dd>
        </dl>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ ofertaEmpleo }: IRootState) => ({
  ofertaEmpleoEntity: ofertaEmpleo.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OfertaEmpleoDetail);
