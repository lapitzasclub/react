import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import appConfig from 'app/config/constants';

export const Notifications = props => (
  <NavLink tag={Link} to="/" className="d-flex align-items-center">
    <span>
      <FontAwesomeIcon icon="bell" size="lg" color="white" />
    </span>
  </NavLink>
);

export const Brand = props => (
  <NavbarBrand tag={Link} to="/" className="brand-logo float-left">
    <span className="brand-title">
      <Translate contentKey="global.title">IVAP</Translate>
    </span>
    <span className="navbar-version">{appConfig.VERSION}</span>
  </NavbarBrand>
);
