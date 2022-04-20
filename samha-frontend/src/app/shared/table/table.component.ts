import {Component, Input, OnInit} from '@angular/core';
import {Observable, of} from 'rxjs';
import {DataService} from '../data.service';
import {catchError, tap} from 'rxjs/operators';

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
  @Input() columns: any[];
  dataSource$: Observable<any>;
  displayedColumns: string[] = [];
  toolbarHeader: string;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.defineDisplayedColumns()
    this.dataSource$ = this.carregarUsuarios();
    this.defineToolbarHeader();
  }

  carregarUsuarios(){
    return this.dataService.getAll(this.resource).pipe(tap(
      next => {
        console.log(next);
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
  }

  private defineToolbarHeader() {
    this.toolbarHeader = this.resource.charAt(0).toUpperCase() + this.resource.slice(1);
  }
}
