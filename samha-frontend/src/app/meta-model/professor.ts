import {TableColumnModel} from './table-column-model';

export const professorColumns: TableColumnModel[] = [
  {columnDef: 'professor_id', header: 'professor_id', visible: false},
  {columnDef: 'nome', header: 'Nome', visible: true},
  {columnDef: 'matricula', header: 'Matrícula', visible: true},
  {columnDef: 'email', header: 'Email', visible: true},
  {columnDef: 'cargaHoraria', header: 'CargaHoraria', visible: true},
]
