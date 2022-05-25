import { Component, OnInit } from '@angular/core';
import {TableComponent} from '../table/table.component';
import {DataService} from '../service/data.service';
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {cursoLogColumns} from '../../meta-model/curso';
import {Filter, Predicate, QueryMirror} from '../query-mirror';
import {Page, PagedList} from '../paged-list';
import {catchError} from 'rxjs/operators';
import {Observable, of} from 'rxjs';
import {DomSanitizer} from '@angular/platform-browser';
import {professorColumns, professorLogColumns} from '../../meta-model/professor';
import {disciplinaColumns} from '../../meta-model/disciplina';
import {turmaLogColumns} from '../../meta-model/turma';
import {usuarioColumns} from '../../meta-model/usuario';
import {coordenadoriaColumns} from '../../meta-model/coordenadoria';
import {eixoColumns} from '../../meta-model/eixo';
import {matrizColumns} from '../../meta-model/matriz-curricular';

@Component({
  selector: 'samha-log',
  templateUrl: './log.component.html',
  styleUrls: ['../table/table.component.css']
})
export class LogComponent extends TableComponent implements OnInit {

  constructor(dataService: DataService,
              formBuilder: FormBuilder,
              router: Router,
              route: ActivatedRoute,
              dialog: MatDialog,
              sanitizer: DomSanitizer) {
    super(dataService, formBuilder, router, route, dialog, sanitizer);
  }

  ngOnInit(): void {
    if(this.resource === undefined && this.columns === undefined) {
      this.setParametersByUrl();
    }
    this.defineDisplayedColumns();
    this.displayedColumns.pop(); //dropa coluna actions para nao precisar sobrescrever o método
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

  setParametersByUrl() {
    this.resource = this.router.url.split('/')[1];
    this.defineColumns();
  }

  defineColumns() {
    switch (this.resource) {
      case 'professor':
        this.columns = professorLogColumns;
        break;
      case 'coordenador':
        this.columns = professorColumns;
        break;
      case 'disciplina':
        this.columns = disciplinaColumns;
        break;
      case 'curso':
        this.columns = cursoLogColumns;
        break;
      case 'oferta':
        this.columns = [];
        break;
      case 'MenuEnum.RELATORIOS':
        this.columns = [];
        break;
      case 'turma':
        this.columns = turmaLogColumns;
        break;
      case 'usuario':
        this.columns = usuarioColumns;
        break;
      case 'coordenadoria':
        this.columns = coordenadoriaColumns;
        break;
      case 'eixo':
        this.columns = eixoColumns;
        break;
      case 'matrizCurricular':
        this.columns = matrizColumns;
        break;
    }
  }

}
