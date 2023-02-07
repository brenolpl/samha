import {TableColumnModel} from './table-column-model';

export const alocacaoColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'Id', visible: false},
  {columnDef: 'disciplina.nome', header: 'Disciplina', visible: true},
  {columnDef: 'professor1.nome', header: 'Professor 1', visible: true},
  {columnDef: 'professor2.nome', header: 'Professor 2', visible: true},
  {columnDef: 'ano', header: 'Ano', visible: true},
  {columnDef: 'semestre', header: 'Semestre', visible: true},
]

// private Turma turma;
