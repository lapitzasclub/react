import { Moment } from 'moment';

export interface IPublicacion {
  id?: number;
  title?: string;
  date?: string;
  summary?: string;
  content?: string;
}

export const defaultValue: Readonly<IPublicacion> = {};
