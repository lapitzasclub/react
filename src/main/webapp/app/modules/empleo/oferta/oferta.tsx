import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import { Card, CardTitle, CardText, Button } from 'reactstrap';

import './oferta.scss';

export interface IOferta {
    title: string;
    place: string;
    slots: string;
    contract: string;
    term: string;
}

export default class Oferta extends React.Component<IOferta> {
    render() {
        const { title, place, slots, contract, term, children } = this.props;

        return (
            <Card body className="mb-2">
                <CardTitle tag="h5">{title}</CardTitle>
                <CardText>
                    <span>
                        <FontAwesomeIcon icon="map-marker-alt" /> {place}
                    </span>
                </CardText>
                <CardText>
                    <span>
                        <FontAwesomeIcon icon="user-friends" /> Nº de plazas: {slots}
                    </span>
                </CardText>
                <CardText>
                    <span>
                        <FontAwesomeIcon icon="briefcase" /> Tipo de contrato: {contract}
                    </span>
                </CardText>
                <CardText>
                    <span>
                        <FontAwesomeIcon icon="calendar-alt" /> {term}
                    </span>
                </CardText>
                <Button>Go somewhere</Button>
            </Card>
        );
    }
}