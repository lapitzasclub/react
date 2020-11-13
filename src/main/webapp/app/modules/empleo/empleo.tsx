import './empleo.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import Oferta from './oferta/oferta';

export type IEmpleoProp = StateProps;

export const Empleo = (props: IEmpleoProp) => {
  const ofertas = [
    {
      title: 'Técnico de administración general',
      place: 'Ayuntamiento de Amorebieta-Etxano (Amorebieta - Bizkaia)',
      slots: '2',
      contract: 'Funcionaria/o',
      term: 'Plazo de inscripción abierto (13/07/2020 - 07/08/2020)'
    }, {
      title: 'Administrativa/o',
      place: 'Instituto Foral de Asistencia Social de Bizkaia (IFAS) (Bizkaia)',
      slots: '25',
      contract: 'Funcionaria/o',
      term: 'Plazo de inscripción abierto (11/07/2020 - 30/07/2020)'
    },
  ];

  return (
    <Row>
      <Col md="12">
        <h2>Empleo público</h2>

        <div className="ofertas">
          {ofertas.map((o, index) => {
            return <Oferta key={index} title={o.title} place={o.place} slots={o.slots} contract={o.contract} term={o.term} />
          })}
        </div>
      </Col>
    </Row >
  )
};

const mapStateToProps = (storeState) => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Empleo);
