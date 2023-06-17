import {TableColumnModel} from './table-column-model';

export const usuarioColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'Id', visible: true},
  {columnDef: 'login', header: 'Login', visible: true},
  {columnDef: 'papel.nome', header: 'Papel', visible: true},
]

export interface Usuario {
  id: number;
  nome: string;
  email: string;
  matricula: string;
}
