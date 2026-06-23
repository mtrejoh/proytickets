export interface TicketModelTs {
  id?: string;
  asignado: string;
  usuario: string;
  nomAsignado: string;
  nomSolicitado: string;
  codigo: string;
  titulo: string;
  descripcion: string;
  modulo: string;
  estado:    'ABIERTO' | 'DESARROLLO' | 'RESUELTO' | 'CERRADO' | 'CANCELADO';
  prioridad: 'BAJA' | 'NORMAL' | 'MEDIA' | 'ALTA' | 'CRITICA';
  creado?: Date;
  finicio?: Date;
  fentrega?: Date;
  horasInvertidas?: number;
}
