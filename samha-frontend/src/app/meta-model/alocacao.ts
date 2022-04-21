import {TableColumnModel} from './table-column-model';

export const alocacaoColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'Id', visible: false},
  {columnDef: 'ano', header: 'Ano', visible: true},
  {columnDef: 'semestre', header: 'Semestre', visible: true},
  {columnDef: 'professor1.nome', header: 'Professor 1', visible: true},
  {columnDef: 'disciplina.matriz.nome', header: 'Matriz', visible: true}
]

// private Turma turma;
