import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Notificacion from './notificacion';
import NotificacionDetail from './notificacion-detail';
import NotificacionUpdate from './notificacion-update';
import NotificacionDeleteDialog from './notificacion-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NotificacionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NotificacionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NotificacionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Notificacion} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={NotificacionDeleteDialog} />
  </>
);

export default Routes;
