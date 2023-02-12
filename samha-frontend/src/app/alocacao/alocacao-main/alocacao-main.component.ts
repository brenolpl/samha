import {Component, EventEmitter, OnInit} from '@angular/core';
import {disciplinaColumns} from "../../meta-model/disciplina";
import {Observable} from "rxjs";
import {DataService} from "../../shared/service/data.service";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {MatOptionSelectionChange} from "@angular/material/core";
import {Filter, QueryMirror} from "../../shared/query-mirror";
import {matrizColumns} from "../../meta-model/matriz-curricular";
import {tap} from "rxjs/operators";
import {PagedList} from "../../shared/paged-list";
import {professorColumns} from "../../meta-model/professor";
import {MatRadioChange} from "@angular/material/radio";

@Component({
  selector: 'samha-alocacao-main',
  templateUrl: './alocacao-main.component.html',
  styleUrls: ['./alocacao-main.component.css']
})
export class AlocacaoMainComponent implements OnInit {

  //Bloco 1
  public disciplinaColumns = disciplinaColumns;
  public disciplinaDisplayedColumns = [];
  public selectedDisciplinaRowIndex: number;
  public disciplinaDataSource$: Observable<any>;
  public cursoControl = new FormControl();
  public disciplinaForm: FormGroup;
  public matriz$: Observable<any>;
  public showPeriodo: boolean = true;
  public qtPeriodos: number = 1;

  //Bloco 2
  public eixoControl = new FormControl();
  public professorForm: FormGroup;
  public professorColumns = professorColumns;
  public selectedProfessorRowIndex: number;
  public professor$: Observable<any>;
  public searchText: string;

  constructor(private dataService: DataService,
              private formBuilder: FormBuilder) {
    this.disciplinaForm = this.formBuilder.group({
      periodo: [1],
      matriz: [null]
    });

    this.professorForm = this.formBuilder.group({
      eixo: [1],
      search: ['']
    });
  }

  ngOnInit(): void {
    this.disciplinaDisplayedColumns.push('nome');
  }

  findColumnValue = (row, column): string => <string> column.split('.').reduce((acc, cur) => acc[cur], row);





  compareFunction(o1: any, o2: any) {
    return (o1 != null && o2 != null && o1.id == o2.id);
  }

  highlightDisciplina(row) {
    this.selectedDisciplinaRowIndex = row.id;
  }

  onCursoChange($event: any) {
    this.disciplinaForm.get('periodo').setValue('1');
    this.qtPeriodos = this.cursoControl.value.qtPeriodos;
    this.loadMatrizes(this.cursoControl.value.id);
  }

  onMatrizChanged($event: MatOptionSelectionChange<any>) {
    this.disciplinaForm.get('matriz').setValue($event.source.value);
    this.onFilterChange();
  }

  onFilterChange() {
    let query = new QueryMirror('disciplina');
    query.projections = disciplinaColumns.map(column => column.columnDef);
    query.orderBy('nome asc');
    query.where({
      and: {
        'matriz.id': {equals: this.disciplinaForm.get('matriz').value.id},
        'periodo': {equals: this.disciplinaForm.get('periodo').value}
      }
    });

    this.disciplinaDataSource$ = this.dataService.query(query);
  }

  onLoaded($event: any[]) {
    this.cursoControl.setValue($event[0]);
    this.qtPeriodos = this.cursoControl.value.qtPeriodos;
    this.loadMatrizes(this.cursoControl.value.id);
  }

  private loadMatrizes(id: number) {
    let query = new QueryMirror('matrizCurricular');
    let projections = matrizColumns.map(column => column.columnDef);
    let filter: Filter;
    filter = {
      and: {
        'curso.id': {equals: id}
      }
    }
    query.selectList(projections);
    query.where(filter)

    this.matriz$ = this.dataService.query(query).pipe(
      tap( (matrizes:PagedList) => this.disciplinaForm.get('matriz').setValue(matrizes.listMap[0]))
    );
  }

  defineDataSource(dataSource: PagedList) {
    return dataSource.listMap;
  }

  onEixoChange($event: any) {
    this.loadProfessores(this.eixoControl.value);
  }

  onEixoLoaded($event: any) {
    this.eixoControl.setValue($event[0]);
    this.loadProfessores($event[0]);
  }

  private loadProfessores($eventElement: any) {
    let query = new QueryMirror('professor');
    query.projections = professorColumns.map(column => column.columnDef);
    query.orderBy('nome asc');
    let and = {};
    if(this.professorForm.get('eixo').value == 1) {
      Object.assign(and, {'coordenadoria.eixo.id': {equals: $eventElement.id}});
    }

    if(this.searchText) {
      Object.assign(and, {'nome': {contains: this.searchText}});
    }

    if(Object.entries(and).length !== 0) {
      query.where({
        and: and
      });
    }




    this.professor$ = this.dataService.query(query);
  }

  highlightProfessor(row) {
    this.selectedProfessorRowIndex = row.id;
  }

  onRadioChange($event: MatRadioChange) {
    this.onEixoLoaded([this.eixoControl.value]);
  }

  onSearchChange() {
    this.searchText = this.professorForm.get('search').value;
    this.loadProfessores(this.eixoControl.value, );
  }
}
