import {Component, OnDestroy, OnInit} from '@angular/core';
import {Observable, Subscription} from 'rxjs';
import {DataService} from '../../shared/service/data.service';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {Page, PagedList} from '../../shared/paged-list';
import {TableColumnModel} from '../../meta-model/table-column-model';
import {turmaColumns} from '../../meta-model/turma';
import {Filter, Predicate, QueryMirror} from '../../shared/query-mirror';
import {$e} from 'codelyzer/angular/styles/chars';

@Component({
  selector: 'samha-turma-list',
  templateUrl: './turma-list.component.html',
  styleUrls: ['./turma-list.component.css']
})
export class TurmaListComponent implements OnInit, OnDestroy {
  dataSource$: Observable<any>;
  columns: TableColumnModel[] = turmaColumns;
  ativoControlSubs: Subscription;
  filtroAtivo: boolean = false;
  displayedColumns: string[];
  ativaControl = new FormControl();
  anoControl = new FormControl();
  semestreControl = new FormControl();
  nivelControl = new FormControl();
  pagedList: PagedList;
  predicates: Predicate[] = []

  constructor(private dataService: DataService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.ativaControl.setValue(false);
    this.anoControl.setValue('2018');
    this.anoControl.disable();
    this.semestreControl.disable();
    this.semestreControl.setValue('1');
    this.ativoControlSubs = this.ativaControl.valueChanges.subscribe(
      next => {
        if(next){
          this.anoControl.enable()
          this.semestreControl.enable()
          // this.executeQuery();
        }else {
          this.anoControl.disable()
          this.semestreControl.disable()
        }
      }
    )
    // this.dataSource$ = this.executeQuery();
    this.defineDisplayedColumns();
  }

  defineDataSource(dataSource: PagedList) {
    this.pagedList = new PagedList(dataSource);
    return dataSource.listMap;
  }

  findColumnValue = (row, column): string => <string> column.split('.').reduce((acc, cur) => acc[cur], row);

  private defineDisplayedColumns() {
    this.displayedColumns = this.columns.map(column => column.columnDef);
  }

  private executeQuery(): Observable<any> {
    let query = new QueryMirror('turma');
    let projections = this.columns.map(column => column.columnDef);
    query.selectList(projections);
    // query.where(filter);

    return this.dataService.query(query);
  }

  compareFunction(o1: string, o2: string) {
    return (o1 != null && o2 != null && o1.toUpperCase() == o2.toUpperCase());
  }

  ngOnDestroy() {
    this.ativoControlSubs.unsubscribe();
  }

  loadNivelFilter($event: any) {
    let predicate: Predicate = {
      and: {
        ativas: this.ativaControl.value,
        semestre: this.semestreControl.value,
        ano: this.anoControl.value,
        nivel: $event
      }
    }
    this.predicates = [predicate];
  }
}
