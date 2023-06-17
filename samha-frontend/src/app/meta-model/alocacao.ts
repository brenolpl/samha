import {TableColumnModel} from './table-column-model';
import {commonLogColumns} from "./log";

export const alocacaoColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'Id', visible: false},
  {columnDef: 'disciplina.sigla', header: 'Disciplina', visible: true},
  {columnDef: 'disciplina.periodo', header: 'Período', visible: true},
  {columnDef: 'professor1.nome', header: 'Professor 1', visible: false},
  {columnDef: 'professor2.nome', header: 'Professor 2', visible: false},
  {columnDef: 'ano', header: 'Ano', visible: false},
  {columnDef: 'semestre', header: 'Semestre', visible: false},
]

export const alocacaoLogColumns: TableColumnModel[] = [
  ...commonLogColumns,
  ...alocacaoColumns.filter(column => column.columnDef !== 'id')
]
