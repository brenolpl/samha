import {Component, Input, OnInit} from '@angular/core';
import {Observable, of} from 'rxjs';
import {DataService} from '../data.service';
import {catchError, tap} from 'rxjs/operators';

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

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.defineDisplayedColumns()
    this.dataSource$ = this.carregarUsuarios();
  }

  carregarUsuarios(){
    return this.dataService.getAll(this.resource).pipe(tap(
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
  }
}
