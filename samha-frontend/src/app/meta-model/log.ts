import {TableColumnModel} from './table-column-model';
import {FieldEnum} from '../shared/field-enum';

export const commonLogColumns: TableColumnModel[] = [
  {columnDef: 'pk.id', header: 'ID', visible: true},
  {columnDef: 'pk.rev', header: 'Revisão', visible: true},
  {columnDef: 'revtype', header: 'Operação', visible: true, type: FieldEnum.OPERATION},
  {columnDef: 'modifiedBy', header: 'Modificado por', visible: true},
  {columnDef: 'modifiedDate', header: 'Data de Alteração', visible: true, type: FieldEnum.DATE},
]
