import { Moment } from 'moment';

export interface INotificacion {
  id?: number;
  date?: string;
  isRead?: boolean;
  title?: string;
  summary?: string;
}

export const defaultValue: Readonly<INotificacion> = {
  isRead: false,
};
