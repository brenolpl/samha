import {ColumnModel} from './column-model';
import {TableModel} from './table-model';

export const tableSymbol = Symbol("table");

export function Column(options: Partial<ColumnModel> = {}) {
  return function(target: any, propertyKey: string) {
    if (!target[tableSymbol]) {
      target[tableSymbol] = new TableModel();
    }
    options.key = options.key || propertyKey;
    const columnOptions = new ColumnModel(options);
    target[tableSymbol].addColumn(columnOptions);
  };
}
