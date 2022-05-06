import {TableColumnModel} from './table-column-model';

export const coordenadoriaColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'id', visible: false},
  {columnDef: 'nome', header: 'Nome', visible: true},
  {columnDef: 'eixo.nome', header: 'Eixo', visible: true}
]
