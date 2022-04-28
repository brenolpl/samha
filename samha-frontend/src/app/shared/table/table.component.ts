import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Observable, of} from 'rxjs';
import {DataService} from '../service/data.service';
import {catchError} from 'rxjs/operators';
import {Filter, Predicate, QueryMirror} from '../query-mirror';
import {Page, PagedList} from '../paged-list';
import {TableColumnModel} from '../../meta-model/table-column-model';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Route, Router} from '@angular/router';
import {MenuEnum} from '../menu-enum';
import {professorColumns} from '../../meta-model/professor';
import {alocacaoColumns} from '../../meta-model/alocacao';
import {disciplinaColumns} from '../../meta-model/disciplina';
import {cursoColumns} from '../../meta-model/curso';
import {turmaColumns} from '../../meta-model/turma';

/**
 * Este componente gera dinamicamente uma tabela de acordo com os parâmetros passados
 * @param resource recebe o endpoint respectivo nos controllers do backend, exemplo: api/usuario, neste caso o resource seria apenas
 * 'usuario', pois 'api/' é um prefixo da aplicação
 * @param columns representa as colunas que serão mostradas na tabela, este parâmetro vêm de constantes exportadas no pacote meta-model
 *
 * @author Breno Leal -- 19/04/2022
 */
@Component({
  selector: 'samha-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {
  @Input() resource: string;
  @Input() columns: TableColumnModel[];
  @Output() onSelectedRow: EventEmitter<number> = new EventEmitter<number>();
  pagedList: PagedList;
  tenButtonSelected: boolean = true;
  fiftyButtonSelected: boolean = false;
  handredButtonSelected: boolean = false;
  currentPage: number = 1;
  maxRows: number = 10;
  lastPage: number;
  fowardButtonDisabled: boolean = false;
  backwardButtonDisabled: boolean = true;
  dataSource$: Observable<PagedList>;
  displayedColumns: string[] = [];
  toolbarHeader: string;
  orderBy: string = '';
  group: FormGroup;

  constructor(private dataService: DataService,
              private formBuilder: FormBuilder,
              private router: Router,
              private route: ActivatedRoute) {
    this.group = this.formBuilder.group({
      search: [null]
    });
  }

  ngOnInit(): void {
    this.setParametersByUrl();
    this.defineDisplayedColumns();
    this.dataSource$ = this.loadTableData();
    this.defineToolbarHeader();
  }

  private loadTableData(filter: Predicate[] = []): Observable<PagedList> {
    const query = new QueryMirror(this.resource);
    let projections: string[] = [];
    let page: Page = {
      size: this.maxRows,
      skip: this.calculateSkip()
    };
    let orFilter: Filter = {
      and: {
        or: filter
      }
    };
    query.pageItem(page);
    this.columns.map(column => projections.push(column.columnDef));
    query.selectList(projections);
    if (filter.length > 0) {
      query.where(orFilter);
    }
    if (this.orderBy !== '') {
      query.orderBy(this.orderBy);
    }
    return this.dataService.query(query).pipe(
      catchError(_ => {
        let empty = {
          listMap: [],
          page: {
            size: 0,
            skip: 0,
            totalItems: 0
          }
        };
        return of(new PagedList(empty));
      })
    );
  }

  private defineDisplayedColumns() {
    this.columns.map(column => {
      if (column.visible) {
        this.displayedColumns.push(column.columnDef);
      }
    });
    this.displayedColumns.push('actions');
  }

  private defineToolbarHeader() {
    this.toolbarHeader = this.resource.charAt(0).toUpperCase() + this.resource.slice(1);
  }

  onEditClick(row: any) {
  }

  onDeleteClick(row: any) {

  }

  /**
   * Reference: https://stackoverflow.com/questions/63414910/display-a-nested-object-in-a-dynamic-material-table-template
   * @param row
   * @param column
   */
  findColumnValue = (row, column): string => <string> column.split('.').reduce((acc, cur) => acc[cur], row);


  setPageSize(value: number) {
    this.maxRows = value;
    switch (value) {
      case 10:
        this.tenButtonSelected = true;
        this.fiftyButtonSelected = false;
        this.handredButtonSelected = false;
        this.dataSource$ = this.loadTableData();
        break;
      case 50:
        this.tenButtonSelected = false;
        this.fiftyButtonSelected = true;
        this.handredButtonSelected = false;
        this.dataSource$ = this.loadTableData();
        break;
      case 100:
        this.tenButtonSelected = false;
        this.fiftyButtonSelected = false;
        this.handredButtonSelected = true;
        this.dataSource$ = this.loadTableData();
        break;
    }
  }

  defineDataSource(dataSource: PagedList) {
    this.pagedList = new PagedList(dataSource);
    this.lastPage = this.calculateLastPage();
    this.checkButtons();
    return dataSource.listMap;
  }

  fowardPage() {
    this.currentPage++;
    this.checkButtons();
    this.dataSource$ = this.loadTableData();
  }

  backwardPage() {
    this.currentPage--;
    this.checkButtons();
    this.dataSource$ = this.loadTableData();
  }

  private checkButtons() {
    this.backwardButtonDisabled = this.currentPage <= 1;
    this.fowardButtonDisabled = this.currentPage >= this.lastPage;
  }

  private calculateLastPage = () => Math.ceil(this.pagedList.page.totalItems / this.maxRows);
  selectedRowIndex: number = 0;

  private calculateSkip(): number {
    if (this.pagedList !== null && this.pagedList !== undefined) {
      this.onSelectedValueChanged();
    }
    return this.maxRows * (this.currentPage - 1);
  }

  private onSelectedValueChanged(): void {
    if (this.pagedList.page.totalItems != 0) {
      let lastPage = this.calculateLastPage();
      while (this.currentPage > lastPage) {
        --this.currentPage;
      }
    } else {
      this.currentPage = 1;
    }
  }

  sort(columnDef: string) {
    if (this.orderBy !== '' && this.orderBy.includes(columnDef)) {
      if (this.orderBy.includes('asc')) {
        this.orderBy = this.orderBy.replace('asc', 'desc');
      } else {
        this.orderBy = '';
      }
    } else {
      this.orderBy = columnDef + ' asc';
    }
    this.dataSource$ = this.loadTableData();
  }

  showArrowUp(columnDef: string): boolean {
    return this.orderBy.includes(columnDef + ' asc');
  }

  showArrowDown(columnDef: string): boolean {
    return this.orderBy.includes(columnDef + ' desc');
  }

  onChanged() {
    let filter: Predicate[] = [];
    if (this.group.value.search !== '') {
      let projections = this.columns.filter(column => column.columnDef !== 'id');
      projections.forEach(column => {
        filter.push({
          [column.columnDef]: {
            contains: this.group.value.search
          }
        });
      });
    }
    this.dataSource$ = this.loadTableData(filter);
  }

  highlight(row) {
    this.selectedRowIndex = row.id;
    this.onSelectedRow.emit(row.id);
  }

  goToNew() {
    this.router.navigate([this.resource + '/new']);
  }

  private setParametersByUrl() {
    this.resource = this.router.url.replace('/', '');
    this.defineColumns();
  }

  private defineColumns() {
    switch (this.resource) {
      case 'professor':
        this.columns = professorColumns;
        break;
      case 'coordenador':
        this.columns = professorColumns;
        break;
      case 'disciplina':
        this.columns = disciplinaColumns;
        break;
      case 'curso':
        this.columns = cursoColumns;
        break;
      case 'oferta':
        this.columns = [];
        break;
      case 'MenuEnum.RELATORIOS':
        this.columns = [];
        break;
      case 'turma':
        this.columns = turmaColumns;
        break;
    }
  }
}
