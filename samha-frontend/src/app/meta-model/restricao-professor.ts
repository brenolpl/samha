import {TableColumnModel} from './table-column-model';

export const restricaoProfessorColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'id', visible: false},
  {columnDef: 'nome', header: 'Nome', visible: true},
  {columnDef: 'descricao', header: 'Descrição', visible: true},
  {columnDef: 'dia', header: 'Dia', visible: true},
  {columnDef: 'turno', header: 'Turno', visible: true},
  {columnDef: 'aula1', header: 'Aula 1', visible: true},
  {columnDef: 'aula2', header: 'Aula 2', visible: true},
  {columnDef: 'aula3', header: 'Aula 3', visible: true},
  {columnDef: 'aula4', header: 'Aula 4', visible: true},
  {columnDef: 'aula5', header: 'Aula 5', visible: true},
  {columnDef: 'aula6', header: 'Aula 6', visible: true},
  {columnDef: 'prioridade', header: 'Prioridade', visible: true},
]

export enum DiaSemanaEnum {
  SEGUNDA ,
  TERCA,
  QUARTA,
  QUINTA,
  SEXTA
}
