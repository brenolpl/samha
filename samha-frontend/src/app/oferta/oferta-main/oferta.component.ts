import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DataService} from "../../shared/service/data.service";
import {NotificationService} from "../../shared/service/notification.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {first, map, startWith} from "rxjs/operators";
import {QueryMirror} from "../../shared/query-mirror";
import {alocacaoColumns} from "../../meta-model/alocacao";
import {
  CdkDragDrop,
  moveItemInArray,
  copyArrayItem,
  transferArrayItem,
  CdkDrag,
  CdkDropList
} from "@angular/cdk/drag-drop";


@Component({
  selector: 'samha-oferta',
  templateUrl: './oferta.component.html',
  styleUrls: ['./oferta.component.css']
})
export class OfertaComponent implements OnInit {
  public cursoControl = new FormControl();
  public turmaControl = new FormControl();
  public formGroup: FormGroup;
  public filteredOptions: Observable<any[]>;
  public alocacao$: Observable<any>;
  public alocacaoColumns = alocacaoColumns;
  public alocacaoDisplayedColumns = alocacaoColumns.filter(c => c.visible).map(c => c.columnDef);
  public qtPeriodos = 1;
  public alocacoes = [];
  public segundaArray = ['', '', '', '', '', ''];
  public tercaArray = ['', '', '', '', '', ''];
  public quartaArray = ['', '', '', '', '', ''];
  public quintaArray = ['', '', '', '', '', ''];
  public sextaArray = ['', '', '', '', '', ''];
  public horarioNoturno = [
    '18:50 a 19:35',
    '19:35 a 20:20',
    '20:30 a 21:15',
    '21:15 a 22:00',
    '-',
    '-'
  ];
  public horarioVespertino = [
    '12:50 a 13:40',
    '13:45 a 14:35',
    '14:40 a 15:30',
    '15:50 a 16:40',
    '16:45 a 17:35',
    '17:40 a 18:30'
  ];
  public horarioMatutino = [
    '07:00 a 07:50',
    '07:55 a 08:45',
    '08:50 a 09:40',
    '10:00 a 10:50',
    '10:55 a 11:45',
    '11:50 a 12:40'
  ]
  private list: any[];


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private dataService: DataService,
    private notification: NotificationService,
    private formBuilder: FormBuilder
  ) {
    this.formGroup = formBuilder.group({
      turno: ['Matutino'],
      ano: [new Date().getUTCFullYear()],
      semestre: [1],
      periodo: [1]
    });
  }

  ngOnInit(): void {
  }

  goToLog() {
    this.router.navigate(['log'], {relativeTo: this.route});
  }

  onCursoChange(_) {
    this.qtPeriodos = this.cursoControl.value.qtPeriodos;
    this.loadTurmas();
  }

  onCursoLoaded($event: any[]) {
    this.cursoControl.setValue($event[0]);
    this.qtPeriodos = this.cursoControl.value.qtPeriodos;
    this.loadTurmas();
  }

  compareFunction = (o1: string, o2: string) => (o1 != null && o2 != null && o1.toUpperCase() == o2.toUpperCase());
  displayFn = (entity: any): string => entity && entity.nome ? entity.nome : '';

  private _filter(nome: string): any[] {
    const filterValue = nome.toLowerCase();
    return this.list.filter(entity => entity.nome.toLowerCase().includes(filterValue));
  }


  private loadTurmas() {
    this.dataService.query(
      new QueryMirror('turma')
        .selectList(['id', 'nome', 'matriz.id'])
        .where({
            and: {
              'matriz.curso.id': {equals: this.cursoControl.value.id},
              'ativa': {equals: true}
            }
          }
        )
    ).pipe(first()).subscribe(
      data => {
        this.list = data.listMap;
        if(this.list.length > 0 && !this.turmaControl.value) {
          this.turmaControl.setValue(this.list[0]);
          this.onTurmaChange();
        }
        this.filteredOptions = this.turmaControl.valueChanges.pipe(
          startWith(''),
          map(value => (typeof value === 'string' ? value : value?.name)),
          map(name => (name ? this._filter(name) : this.list.slice())),
        );
      }
    )
  }

  onTurnoChange() {
    this.loadTurmas();
  }

  onAnoChange() {
    this.dataService.query(new QueryMirror('alocacao')
      .selectList(['id', 'disciplina.sigla', 'professor1.nome', 'professor2.nome', 'ano', 'semestre'])
      .where({
        and: {
          'disciplina.periodo': {equals: this.formGroup.get('periodo').value},
          'ano': {equals: this.formGroup.get('ano').value},
          'semestre': {equals: this.formGroup.get('semestre').value},
          'disciplina.matriz.id': {equals: this.turmaControl.value.matriz.id}
        }
      })
    ).pipe(first())
      .subscribe(
        data => {
          this.alocacoes = data.listMap
        }
      )
  }

  noReturnPredicate() {
    return false;
  }

  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else if (event.previousContainer.id === 'primary-list') {
      this.copyWithTradingArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex);
    } else {
      this.tradeArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      )
    }
  }

  tradeArrayItem(currentArray: any[], targetArray: any[], currentIndex: number, targetIndex: number) {
    let itemFromCurrentArray = currentArray[currentIndex];
    let itemFromTargetArray = targetArray[targetIndex];
    if(itemFromTargetArray !== undefined) {
      currentArray[currentIndex] = itemFromTargetArray;
      targetArray[targetIndex] = itemFromCurrentArray;
    }
  }

  copyWithTradingArrayItem(currentArray: any[], targetArray: any[], currentIndex: number, targetIndex: number) {
    let itemFromCurrentArray = currentArray[currentIndex];
    let itemFromTargetArray = targetArray[targetIndex];
    if(itemFromTargetArray !== undefined) {
      targetArray[targetIndex] = itemFromCurrentArray;
    }
  }

  onTurmaChange() {
    this.dataService.get('turma/getPeriodoAtual', this.turmaControl.value.id)
      .pipe(first())
      .subscribe(data => {
        this.formGroup.get('periodo').setValue(data);
        this.onAnoChange();
      })
  }

  getNomeEncurtadoProfessor(nome: string) {
    if(nome != null) {
      let nomes = nome.split(' ');
      let siglas = nomes.map(n => n.substring(0, 1)).splice(1).join('');
      let nomeEncutado = nomes[0] + ' ' + siglas;
      return nomeEncutado;
    }

    return '';
  }

  getListValue(alocacao: any) {
    if(typeof alocacao === 'string') return alocacao
    else return alocacao.disciplina.sigla + ' - ' + this.getNomeEncurtadoProfessor(alocacao.professor1.nome) + ' ' + this.getNomeEncurtadoProfessor(alocacao.professor2?.nome)
  }

  getHorario() {
    let turno = this.formGroup.get('turno').value.toUpperCase();
    if(turno == 'MATUTINO') return this.horarioMatutino;
    else if (turno == 'VESPERTINO') return this.horarioVespertino;
    else return this.horarioNoturno;
  }

  delete(event: CdkDragDrop<any[], any>) {
    this.tradeArrayItem(
      event.previousContainer.data,
      [''],
      event.previousIndex,
      0
    )
  }
}
