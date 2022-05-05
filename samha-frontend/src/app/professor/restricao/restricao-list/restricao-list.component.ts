import {Component, Input, OnInit} from '@angular/core';
import {Observable, of} from 'rxjs';
import {DataService} from '../../../shared/service/data.service';
import {Filter, QueryMirror} from '../../../shared/query-mirror';
import {restricaoProfessorColumns} from '../../../meta-model/restricao-professor';
import {TableColumnModel} from '../../../meta-model/table-column-model';
import { PagedList} from '../../../shared/paged-list';
import {ActivatedRoute, Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {TableDialogComponent} from '../../../shared/table-dialog/table-dialog.component';
import {servidorColumns} from '../../../meta-model/servidor';
import {ConfirmDialogComponent} from '../../../shared/confirm-dialog/confirm-dialog.component';
import {first} from 'rxjs/operators';

@Component({
  selector: 'samha-restricao-list',
  templateUrl: './restricao-list.component.html',
  styleUrls: ['./restricao-list.component.css']
})
export class RestricaoListComponent implements OnInit {
  @Input() professorId: string;
  private query: QueryMirror;
  dataSource$: Observable<any>;
  columns: TableColumnModel[] = restricaoProfessorColumns;
  displayedColumns: string[] = [];
  pagedList: PagedList;

  constructor(private dataService: DataService,
              private router: Router,
              private route: ActivatedRoute,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    this.defineDisplayedColumns();
    let query: QueryMirror = new QueryMirror('restricaoProfessor');
    let projections = this.columns.map(column => column.columnDef);
    query.selectList(projections);
    let filter: Filter = {
      and: {
        'professor.id': {equals: this.professorId}
      }
    };
    query.where(filter);
    this.query = query;
    this.dataSource$ = this.dataService.query(query);
  }

  defineDataSource(dataSource: PagedList) {
    this.pagedList = new PagedList(dataSource);
    return dataSource.listMap;
  }

  private defineDisplayedColumns() {
    this.columns.forEach(column => {
      if (column.visible) {
        this.displayedColumns.push(column.columnDef);
      }
    });
    this.displayedColumns.push('actions');
  }

  findColumnValue = (row, column): string => <string> column.split('.').reduce((acc, cur) => acc[cur], row);

  onEditClick(row) {
    this.router.navigate(['restricaoProfessor/' + row.id], {relativeTo: this.route})
  }

  onDeleteClick(row) {
    this.openDialog(row);
  }

  openDialog(row) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent);

    dialogRef.afterClosed().pipe(first()).subscribe(result => {
      if(result){
        this.dataService.delete('restricaoProfessor', row.id).pipe(first()).subscribe(
          next => {
            this.dataSource$ = this.dataService.query(this.query);
          }
        );
      }
    });
  }

  goToNew() {
    this.router.navigate(['restricaoProfessor/new'], {relativeTo: this.route})
  }
}
