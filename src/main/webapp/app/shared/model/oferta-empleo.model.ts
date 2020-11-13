export interface IOfertaEmpleo {
  id?: number;
  title?: string;
  place?: string;
  slots?: number;
  contract?: string;
  term?: string;
}

export const defaultValue: Readonly<IOfertaEmpleo> = {};
