import {TableColumnModel} from './table-column-model';
import {FieldEnum} from '../shared/field-enum';

export const professorColumns: TableColumnModel[] = [
  {columnDef: 'id', header: 'id', visible: false},
  {columnDef: 'nome', header: 'Nome', visible: true},
  {columnDef: 'matricula', header: 'Matrícula', visible: true},
  {columnDef: 'email', header: 'Email', visible: true},
  {columnDef: 'cargaHoraria', header: 'CargaHoraria', visible: true},
  {columnDef: 'ativo', header: 'Ativo', visible: true, type: FieldEnum.BOOLEAN}
]
