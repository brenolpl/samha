import {TableColumnModel} from './table-column-model';
import {FieldEnum} from '../shared/field-enum';

export const commonLogColumns: TableColumnModel[] = [
  {columnDef: 'revtype', header: 'Operação', visible: true, type: FieldEnum.OPERATION},
  {columnDef: 'createdBy', header: 'Criado por', visible: true},
  {columnDef: 'modifiedBy', header: 'Modificado por', visible: true},
  {columnDef: 'modifiedDate', header: 'Data da modificação', visible: true, type: FieldEnum.DATE},
  {columnDef: 'createdDate', header: 'Data da criação', visible: true, type: FieldEnum.DATE}
]
