import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OfertaEmpleo from './oferta-empleo';
import OfertaEmpleoDetail from './oferta-empleo-detail';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OfertaEmpleoDetail} />
      <ErrorBoundaryRoute path={match.url} component={OfertaEmpleo} />
    </Switch>
  </>
);

export default Routes;
