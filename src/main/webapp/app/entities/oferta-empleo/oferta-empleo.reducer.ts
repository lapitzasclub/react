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

import { IOfertaEmpleo, defaultValue } from 'app/shared/model/oferta-empleo.model';

export const ACTION_TYPES = {
  FETCH_OFERTAEMPLEO_LIST: 'ofertaEmpleo/FETCH_OFERTAEMPLEO_LIST',
  FETCH_OFERTAEMPLEO: 'ofertaEmpleo/FETCH_OFERTAEMPLEO',
  RESET: 'ofertaEmpleo/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOfertaEmpleo>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type OfertaEmpleoState = Readonly<typeof initialState>;

// Reducer

export default (state: OfertaEmpleoState = initialState, action): OfertaEmpleoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OFERTAEMPLEO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OFERTAEMPLEO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_OFERTAEMPLEO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OFERTAEMPLEO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OFERTAEMPLEO_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_OFERTAEMPLEO):
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

const apiUrl = 'api/oferta-empleos';

// Actions

export const getEntities: ICrudGetAllAction<IOfertaEmpleo> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_OFERTAEMPLEO_LIST,
    payload: axios.get<IOfertaEmpleo>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IOfertaEmpleo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OFERTAEMPLEO,
    payload: axios.get<IOfertaEmpleo>(requestUrl),
  };
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
