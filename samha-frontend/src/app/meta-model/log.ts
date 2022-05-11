import {TableColumnModel} from './table-column-model';

export const commonLogColumns: TableColumnModel[] = [
  {columnDef: 'rev', header: 'Número da revisão', visible: true},
  {columnDef: 'revtype', header: 'Operação', visible: true},
  {columnDef: 'createdBy', header: 'Criado por', visible: true},
  {columnDef: 'modifiedBy', header: 'Modificado por', visible: true},
  {columnDef: 'modifiedDate', header: 'Data da modificação', visible: true},
  {columnDef: 'createdDate', header: 'Data da criação', visible: true}
]
