import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OfertaEmpleo from './oferta-empleo';
import Publicacion from './publicacion';
import Notificacion from './notificacion';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}oferta-empleo`} component={OfertaEmpleo} />
      <ErrorBoundaryRoute path={`${match.url}publicacion`} component={Publicacion} />
      <ErrorBoundaryRoute path={`${match.url}notificacion`} component={Notificacion} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
