import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Publicacion from './publicacion';
import PublicacionDetail from './publicacion-detail';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PublicacionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Publicacion} />
    </Switch>
  </>
);

export default Routes;
