import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPublicacion, defaultValue } from 'app/shared/model/publicacion.model';

export const ACTION_TYPES = {
  FETCH_PUBLICACION_LIST: 'publicacion/FETCH_PUBLICACION_LIST',
  FETCH_PUBLICACION: 'publicacion/FETCH_PUBLICACION',
  RESET: 'publicacion/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPublicacion>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PublicacionState = Readonly<typeof initialState>;

// Reducer

export default (state: PublicacionState = initialState, action): PublicacionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PUBLICACION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PUBLICACION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PUBLICACION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PUBLICACION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PUBLICACION_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_PUBLICACION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/publicacions';

// Actions

export const getEntities: ICrudGetAllAction<IPublicacion> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PUBLICACION_LIST,
    payload: axios.get<IPublicacion>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPublicacion> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PUBLICACION,
    payload: axios.get<IPublicacion>(requestUrl),
  };
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
