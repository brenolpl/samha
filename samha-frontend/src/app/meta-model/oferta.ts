import {TableColumnModel} from "./table-column-model";
import {commonLogColumns} from "./log";

export const ofertaColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'id', visible: false},
  {columnDef: 'ano', header: 'Ano', visible: true},
  {columnDef: 'semestre', header: 'Semestre', visible: true},
  {columnDef: 'tempoMaximoTrabalho', header: 'Tempo Máximo Trabalho', visible: true},
  {columnDef: 'intervaloMinimo', header: 'Intervalo Mínimo', visible: true},
  {columnDef: 'turma.nome', header: 'Turma', visible: true}
]

export const ofertaLogColumns: TableColumnModel[] = [
  ...ofertaColumns.filter(f => f.columnDef !== 'id'),
  ...commonLogColumns
]
