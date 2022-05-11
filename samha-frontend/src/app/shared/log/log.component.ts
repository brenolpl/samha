import { Component, OnInit } from '@angular/core';
import {TableComponent} from '../table/table.component';
import {DataService} from '../service/data.service';
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {cursoComLogColumns} from '../../meta-model/curso';
import {Filter, Predicate, QueryMirror} from '../query-mirror';
import {Page, PagedList} from '../paged-list';
import {catchError} from 'rxjs/operators';
import {Observable, of} from 'rxjs';

@Component({
  selector: 'samha-log',
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.css']
})
export class LogComponent extends TableComponent implements OnInit {

  constructor(dataService: DataService,
              formBuilder: FormBuilder,
              router: Router,
              route: ActivatedRoute,
              dialog: MatDialog) {
    super(dataService, formBuilder, router, route, dialog);
  }

  ngOnInit(): void {
    this.columns = cursoComLogColumns;
    this.resource = 'curso';
    if(this.resource === undefined && this.columns === undefined) {
      this.setParametersByUrl();
    }
    this.defineDisplayedColumns();
    this.orderBy = 'modifiedDate desc';
    this.dataSource$ = this.loadTableData();
  }


  loadTableData(filter: Predicate[] = []): Observable<PagedList> {
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
    this.columns.forEach(column => projections.push(column.columnDef));
    query.selectList(projections);
    if (filter.length > 0) {
      query.where(orFilter);
    }
    if (this.orderBy !== '') {
      query.orderBy(this.orderBy);
    }
    return this.dataService.queryLog(query).pipe(
      catchError(_ => {
        let empty = {
          listMap: [],
          list: [],
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

  defineDataSource(dataSource: PagedList) {
    this.pagedList = new PagedList(dataSource);
    this.lastPage = this.calculateLastPage();
    this.checkButtons();
    return dataSource.list;
  }


}
