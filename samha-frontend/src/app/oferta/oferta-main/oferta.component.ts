import {Component, EventEmitter, OnInit} from '@angular/core';
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
  CdkDropList, CdkDragEnter
} from "@angular/cdk/drag-drop";


@Component({
  selector: 'samha-oferta',
  templateUrl: './oferta.component.html',
  styleUrls: ['./oferta.component.css', '../oferta-grid/oferta-grid.component.css']
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
  public matriz = [
    this.segundaArray,
    this.tercaArray,
    this.quartaArray,
    this.quintaArray,
    this.sextaArray
  ]
  private list: any[];
  public novaAula: any;
  private aulasMatutinas: any[] = [];
  private aulasVespertinas: any[] = [];
  private aulasNoturnas: any[] = [];
  private oferta: any;


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
        } else {
          this.buildMatriz();
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
          this.dataService.query(new QueryMirror('oferta')
            .selectList(['id', 'ano', 'semestre', 'tempoMaximoTrabalho', 'intervaloMinimo', 'turma.id'])
            .where({
              and: {
                'ano': {equals: this.formGroup.get('ano').value},
                'semestre': {equals: this.formGroup.get('semestre').value},
                'turma.id': {equals: this.turmaControl.value.id},
                'tempoMaximoTrabalho': {equals: 11},
                'intervaloMinimo': {equals: 11}
              }
            })
          ).pipe(first()).subscribe(
            next => {
              if (next.listMap.length > 0) {
                this.oferta = next.listMap[0];
                this.dataService.query(new QueryMirror('aula')
                  .selectList(['id', 'numero', 'dia', 'turno', 'alocacao.id', 'oferta.id', 'alocacao.disciplina.sigla', 'alocacao.professor1.nome', 'alocacao.professor2.nome'])
                  .where({
                    and: {
                      'oferta.id': {equals: next.listMap[0].id}
                    }
                  })).pipe(first()).subscribe(
                  next => {
                    let list = next.listMap as any[];
                    this.aulasMatutinas = list.filter(a => a.numero <= 5);
                    this.aulasVespertinas = list.filter(a => a.numero > 5 && a.numero <= 11);
                    this.aulasNoturnas = list.filter(a => a.numero > 11);
                    this.buildMatriz();
                  }
                )
              }
            }
          )
        }
      );
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
  onDragStart(event: DragEvent, rowIndex: number, colIndex: number) {
    const target = event.target as HTMLElement;
    target.classList.add('dragging');
    event.dataTransfer?.setData('text/plain', JSON.stringify({ rowIndex, colIndex }));
  }

  onDragEnd(event: DragEvent) {
    event.preventDefault();
    const target = event.target as HTMLElement;
    target.classList.remove('dragging');
    this.novaAula = undefined;
  }

  onListDraggerStart(alocacao: any) {
    let novaAula = {
      alocacao: alocacao,
      dia: null,
      numero: null,
      turma: this.turmaControl.value,
      turno: this.getValorTurno(),
      oferta: this.oferta
    }
    this.novaAula = novaAula;
  }

  private buildMatriz() {
    this.matriz = [[]];
    //preencho a matriz com elementos vazios, para não perder os elementos da tabela de arrasta e solta
    for (let i = 0; i < 5; i++) {
      this.matriz[i] = [];
      for (let j = 0; j < 6; j++) {
        this.matriz[i][j] = '';
      }
    }
    if((this.formGroup.get('turno').value as string).toUpperCase() == 'MATUTINO') {
      this.aulasMatutinas.forEach(aula => this.matriz[aula.dia][aula.numero] = aula);
    } else if ((this.formGroup.get('turno').value as string).toUpperCase() == 'VESPERTINO'){
      this.aulasVespertinas.forEach(aula => this.matriz[aula.dia][aula.numero % 6] = aula);
    } else {
      this.aulasNoturnas.forEach(aula => this.matriz[aula.dia][aula.numero % 6] = aula);
    }
    return this.matriz;
  }

  onAulaChanged(event: any) {
    console.log(event);
    switch (event.turno){
      case 0:
        this.aulasMatutinas.push(event);
        break;
      case 6:
        this.aulasVespertinas[event.numero % 6] = event;
        break;
      case 12:
        this.aulasNoturnas[event.numero % 6] = event;
        break;
    }
    this.buildMatriz();
  }

  private getValorTurno() {
    switch (this.formGroup.get('turno').value.toUpperCase()){
      case 'MATUTINO':
        return 0;
      case 'VESPERTINO':
        return 6;
      case 'NOTURNO':
        return 12;
    }
  }

  onAulaIndexChanged(event: any) {
    if(event.prevItem.item !== undefined && !(event.prevItem.item instanceof String)) this.onAulaChanged(event.prevItem)
    else this.matriz[event.prevItem.prevRowIndex][event.prevItem.prevColIndex] = event.item;
    if(event.currItem.item !== undefined && !(event.currItem.item instanceof String)) this.onAulaChanged(event.currItem)
    else this.matriz[event.currItem.rowIndex][event.currItem.colIndex] = event.item;
  }
}
