export class ColumnModel {
  /** List of options */
  key: string;

  constructor(options: Partial<ColumnModel> = {}) {
    this.key = options.key;
  }
}
