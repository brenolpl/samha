import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Observable, of} from 'rxjs';
import {DataService} from '../service/data.service';
import {catchError, tap} from 'rxjs/operators';
import {QueryMirror} from '../query-mirror';
import {HttpEvent, HttpHeaderResponse, HttpProgressEvent, HttpResponse, HttpSentEvent, HttpUserEvent} from '@angular/common/http';
import {Page, PagedList} from '../paged-list';
import {TableColumnModel} from '../../meta-model/table-column-model';
import {MatButton} from '@angular/material/button';

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
  pagedList: PagedList;
  tenButtonSelected: boolean = true;
  fiftyButtonSelected: boolean = false;
  handredButtonSelected: boolean = false;
  currentPage: number = 1;
  selectedValue: number = 10;
  lastPage: number;
  fowardButtonDisabled: boolean = false;
  backwardButtonDisabled: boolean = true;
  dataSource$: Observable<HttpEvent<PagedList>>;
  displayedColumns: string[] = [];
  toolbarHeader: string;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.defineDisplayedColumns()
    this.dataSource$ = this.carregarUsuarios(this.selectedValue, this.currentPage);
    this.defineToolbarHeader();
  }

  carregarUsuarios(size: number, skip: number){
    const query = new QueryMirror(this.resource);
    let projections: string[] = [];
    let page: Page = {
      size: size,
      skip: skip
    };
    query.pageItem(page);
    this.columns.map(column => projections.push(column.columnDef));
    query.selectList(projections)
    return this.dataService.query(query).pipe(tap(
    next => {

      },
      catchError(error => of([]))
    ))
  }

  private defineDisplayedColumns() {
    this.columns.map(column => {
      if(column.visible){
        this.displayedColumns.push(column.columnDef)
      }
    })
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
  findColumnValue = (row, column): string => <string>column.split('.').reduce((acc, cur) => acc[cur], row);


  setPageSize(value: number) {
    this.selectedValue = value;
    switch(value){
      case 10:
        this.tenButtonSelected = true;
        this.fiftyButtonSelected = false;
        this.handredButtonSelected = false;
        this.dataSource$ = this.carregarUsuarios(value, this.calculateSkip());
        break;
      case 50:
        this.tenButtonSelected = false;
        this.fiftyButtonSelected = true;
        this.handredButtonSelected = false;
        this.dataSource$ = this.carregarUsuarios(value, this.calculateSkip());
        break;
      case 100:
        this.tenButtonSelected = false;
        this.fiftyButtonSelected = false;
        this.handredButtonSelected = true;
        this.dataSource$ = this.carregarUsuarios(value, this.calculateSkip());
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
    this.dataSource$ = this.carregarUsuarios(this.selectedValue, this.calculateSkip());
  }



  backwardPage() {
    this.currentPage--;
    this.checkButtons()
    this.dataSource$ = this.carregarUsuarios(this.selectedValue, this.calculateSkip());
  }

  private checkButtons(){
    if(this.currentPage <= 1) this.backwardButtonDisabled = true; else this.backwardButtonDisabled = false;
    if(this.currentPage >= this.lastPage) this.fowardButtonDisabled = true; else this.fowardButtonDisabled = false;
  }

  private calculateLastPage = () => Math.ceil(this.pagedList.page.totalItems / this.selectedValue);

  private calculateSkip(): number{
    this.onSelectedValueChanged();
    return this.selectedValue * (this.currentPage - 1);
  }

  private onSelectedValueChanged(): void{
    while(this.currentPage > this.calculateLastPage()) --this.currentPage;
  }
}
