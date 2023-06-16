import {Component, ElementRef, EventEmitter, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DataService} from "../../shared/service/data.service";
import {NotificationService} from "../../shared/service/notification.service";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {Observable, of, range, Subscription} from "rxjs";
import {catchError, finalize, first, map, startWith, tap, toArray} from "rxjs/operators";
import {QueryMirror} from "../../shared/query-mirror";
import {alocacaoColumns} from "../../meta-model/alocacao";
import {MatDialog} from "@angular/material/dialog";
import {AlteracaoDialogComponent} from "../../shared/alteracao-dialog/alteracao-dialog.component";
import {PagedList} from "../../shared/paged-list";
import {animate, state, style, transition, trigger} from "@angular/animations";
import notify from "devextreme/ui/notify";
import {HttpEvent, HttpEventType} from "@angular/common/http";


@Component({
  selector: 'samha-oferta',
  templateUrl: './oferta.component.html',
  styleUrls: ['./oferta.component.css', '../oferta-grid/oferta-grid.component.css'],
  animations: [
    trigger('pushInOut', [
      state('void', style({ transform: 'translateX(100%)' })),
      state('*', style({ transform: 'translateX(0)' })),
      transition(':enter', animate('200ms linear')),
      transition(':leave', animate('200ms linear'))
    ]),
    trigger('verticalInOut', [
      state('void', style({ transform: 'translateY(-100%)' })),
      state('*', style({ transform: 'translateY(0)' })),
      transition(':leave', animate('200ms ease-out')),
      transition(':enter', animate('200ms ease-in'))
    ])
  ]
})
export class OfertaComponent implements OnInit, OnDestroy {
  @ViewChild('anoInput', {static: false}) anoInput: ElementRef;
  @ViewChild('semestreInput', {static: false}) semestreInput: ElementRef;
  @ViewChild('periodoInput', {static: false}) periodoInput: ElementRef;
  private validarTurmasSub: Subscription;
  public cursoControl = new FormControl();
  public turmaControl = new FormControl();
  public formGroup: FormGroup;
  public filteredOptions: Observable<any[]>;
  public alocacao$: Observable<any>;
  public alocacaoColumns = alocacaoColumns;
  public alocacaoDisplayedColumns = alocacaoColumns.filter(c => c.visible).map(c => c.columnDef);
  public qtPeriodos = 1;
  public tempoMaximo = '11';
  public intervaloMinimo = '11';
  public ofertaChanged: boolean = false;
  public alocacoes = [];
  public alocacaoSelecionada: any;
  public matriz: any[][] = [[]];
  public oferta: any;
  public notificacoes: any[] = [];
  public aulasConflitantes: any[] = [];
  public filterOpened: boolean = true;
  public notificacoesOpened: boolean = false;
  public notificacaoTurma: boolean = false;
  private list: any[];
  public novaAula: any;
  private aulasMatutinas: any[] = [];
  private aulasVespertinas: any[] = [];
  private aulasNoturnas: any[] = [];
  private turmaCurrentValue: any;
  private cursoCurrentValue: any;
  private anoCurrentValue: any;
  private semestreCurrentValue: any;
  private periodoCurrentValue: any;
  private progresso: number = 0;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private dataService: DataService,
    private notification: NotificationService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog
  ) {
    this.formGroup = formBuilder.group({
      turno: ['Matutino'],
      ano: [new Date().getUTCFullYear()],
      semestre: [1],
      periodo: [1]
    });
    this.anoCurrentValue = this.formGroup.get('ano').value;
    this.semestreCurrentValue = 1;
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
    if (this.ofertaChanged){
      const dialogRef = this.openDialog();
      dialogRef.afterClosed().pipe(first()).subscribe(
        (result: string) => {
          if(result === 'salvar') {
            this.onSalvarClicked();
          } else if (result === 'descartar'){
            this.executeTurmaQuery();
          } else {
            this.cursoControl.setValue(this.cursoCurrentValue);
            this.turmaControl.setValue(this.turmaCurrentValue);
          }
        }
      )
    } else {
      this.executeTurmaQuery();
    }

  }

  onTurnoChange() {
    this.buildMatriz();
  }

  executeTurmaQuery() {
    this.ofertaChanged = false;
    this.dataService.query(
      new QueryMirror('turma')
        .selectList(['id', 'nome', 'matriz.id', 'matriz.curso.id'])
        .where({
            and: {
              'matriz.curso.id': {equals: this.cursoControl.value.id},
              'ativa': {equals: true}
            }
          }
        ).orderBy('nome asc')
    ).pipe(
      first(),
      tap(
        (next: PagedList) => {
          next.listMap.sort((a, b) => {
            if (a.nome > b.nome) return 1;
            else if(a.nome < b.nome) return -1;
            else return 0;
          })
        }
      )
      ).subscribe(
      data => {
        this.list = data.listMap;
        if(this.list.length > 0 && !this.turmaControl.value) {
          this.turmaControl.setValue(this.list[0]);
          this.onTurmaChange();
        } else if(this.turmaControl.value.matriz.curso.id !== this.cursoControl.value.id) {
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

  onAnoChange(value: any) {
    if(this.ofertaChanged) {
      const dialogRef = this.openDialog();
      dialogRef.afterClosed().pipe(first()).subscribe(
        (result: string) => {
          if(result === 'salvar') {
            this.onSalvarClicked()
          } else if(result === 'descartar') {
            this.anoCurrentValue = value;
            this.executeAnoQuery();
          } else {
            this.formGroup.get('ano').setValue(this.anoCurrentValue);
          }
        }
      )
    } else {
      this.anoCurrentValue = value;
      this.executeAnoQuery();
    }
  }

  private executeAnoQuery() {
    this.ofertaChanged = false;
    this.dataService.query(new QueryMirror('alocacao')
      .selectList(['id', 'disciplina.sigla', 'disciplina.id', 'disciplina.tipo', 'professor1', 'professor2', 'ano', 'semestre'])
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
          this.alocacoes = data.listMap;
          this.executeOfertaQuery();
        }
      );
  }


  private executeOfertaQuery() {
    this.dataService.query(new QueryMirror('oferta')
      .selectList(['id', 'ano', 'semestre', 'tempoMaximoTrabalho', 'intervaloMinimo', 'turma.id', 'turma.nome'])
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
            .selectList(['id', 'numero', 'dia', 'turno', 'oferta', 'alocacao.id', 'alocacao.disciplina', 'alocacao.professor1', 'alocacao.professor2', 'alocacao.ano', 'alocacao.semestre'])
            .where({
              and: {
                'oferta.id': {equals: next.listMap[0].id}
              }
            })).pipe(first()).subscribe(
            next => {
              let list = next.listMap as any[];
              if (next.listMap.length > 0) this.executeAulasRestricaoQuery(next.listMap);
              this.aulasMatutinas = list.filter(a => a.numero <= 5);
              this.aulasVespertinas = list.filter(a => a.numero > 5 && a.numero <= 11);
              this.aulasNoturnas = list.filter(a => a.numero > 11);
              this.buildMatriz();
            }
          )
        } else {
          this.oferta = {
            id: null,
            ano: this.formGroup.get('ano').value,
            semestre: this.formGroup.get('semestre').value,
            turma: this.turmaControl.value,
            tempoMaximoTrabalho: this.tempoMaximo,
            intervaloMinimo: this.intervaloMinimo,
          }
          this.aulasVespertinas = [];
          this.aulasMatutinas = [];
          this.aulasNoturnas = [];
          this.buildMatriz();
        }
      }
    )
  }

  onTurmaChange() {
    if (this.ofertaChanged) {
      const dialogRef = this.openDialog();
      dialogRef.afterClosed().pipe(first()).subscribe(
        (result: string) => {
          if(result === 'salvar') {
            this.onSalvarClicked()
          } else if(result === 'descartar') {
            this.executePeriodoAtualQuery();
          } else {
            this.turmaControl.setValue(this.turmaCurrentValue);
          }
        }
      )
    } else {
      this.executePeriodoAtualQuery();
    }
  }

  private executePeriodoAtualQuery() {
    this.ofertaChanged = false;
    this.dataService.get('turma/getPeriodoAtual', this.turmaControl.value.id)
      .pipe(first())
      .subscribe(data => {
        this.formGroup.get('periodo').setValue(data);
        this.periodoCurrentValue = data;
        this.onAnoChange(this.anoCurrentValue);
      });
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

  onDragEnd(event: DragEvent) {
    event.preventDefault();
    const target = event.target as HTMLElement;
    target.classList.remove('dragging');
    this.novaAula = undefined;
  }

  onListDraggerStart(alocacao: any) {
    if (this.oferta.id === null) this.createOferta();
    let novaAula = {
      alocacao: alocacao,
      dia: null,
      numero: null,
      turno: this.getValorTurno(),
      oferta: this.oferta
    }
    this.novaAula = novaAula;
  }

  private buildMatriz() {
    this.matriz = [[]];
    const turno = (this.formGroup.get('turno').value as string).toUpperCase();

    const matriz$: Observable<any[][]> = range(0, 5).pipe(
      map(() => Array.from({ length: 6 }, () => '')),
      toArray()
    );

    matriz$.subscribe(m => {
      this.matriz = m;

      switch (turno) {
        case 'MATUTINO':
          this.aulasMatutinas.forEach(aula => this.matriz[aula.dia][aula.numero] = aula);
          break;
        case 'VESPERTINO':
          this.aulasVespertinas.forEach(aula => this.matriz[aula.dia][aula.numero % 6] = aula);
          break;
        default:
          this.aulasNoturnas.forEach(aula => this.matriz[aula.dia][aula.numero % 6] = aula);
          break;
      }
    });

    return this.matriz;
  }

  onAulaChanged(event: any) {
    this.ofertaChanged = true;
    switch (event.turno){
      case 0:
        this.aulasMatutinas.push(event);
        break;
      case 6:
        this.aulasVespertinas.push(event);
        break;
      case 12:
        this.aulasNoturnas.push(event);
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

  onAulaDeleted(event: any) {
    this.ofertaChanged = true;
    if(event.numero < 6) {
      this.aulasMatutinas = this.aulasMatutinas.filter(a => !(a.dia == event.dia && a.numero == event.numero));
    } else if (event.numero < 12 && event.numero >= 6){
      this.aulasVespertinas = this.aulasVespertinas.filter(a => !(a.dia == event.dia && a.numero == event.numero));
    } else if (event.numero < 16 && event.numero >= 12) {
      this.aulasNoturnas = this.aulasNoturnas.filter(a => !(a.dia == event.dia && a.numero == event.numero));
    }
    this.buildMatriz();
  }

  onSemestreChange(value: any) {
    if(this.ofertaChanged) {
      const dialogRef = this.openDialog();
      dialogRef.afterClosed().pipe(first()).subscribe(
        (result: string) => {
          if(result === 'salvar') {
            this.onSalvarClicked();
          } else if(result === 'descartar') {
            this.semestreCurrentValue = value;
            this.executeAnoQuery();
          } else {
            this.formGroup.get('semestre').setValue(this.semestreCurrentValue);
          }
        }
      )
    } else {
      this.semestreCurrentValue = value;
      this.executeAnoQuery();
    }
  }

  onPeriodoChange(value: any) {
    if (this.ofertaChanged) {
      const dialogRef = this.openDialog();
      dialogRef.afterClosed().pipe(first()).subscribe(
        (result: string) => {
          if (result === 'salvar') {
            this.onSalvarClicked();
          } else if (result === 'descartar') {
            this.periodoCurrentValue = value;
            this.executeAnoQuery();
          } else {
            this.formGroup.get('periodo').setValue(this.periodoCurrentValue);
          }
        }
      )
    } else {
      this.periodoCurrentValue = value;
      this.executeAnoQuery();
    }
  }

  selectAlocacao = (alocacao: any) => {
    if(alocacao.id === this.alocacaoSelecionada?.id) this.alocacaoSelecionada = undefined;
    else this.alocacaoSelecionada = alocacao;
  }
  getAulas = () => [...this.aulasMatutinas, ...this.aulasVespertinas, ...this.aulasNoturnas];
  openDialog = () => this.dialog.open(AlteracaoDialogComponent);
  onTurmaSelectOpened = () => this.turmaCurrentValue = this.turmaControl.value;
  onCursoSelectionOpened = () => this.cursoCurrentValue = this.cursoControl.value;

  private executeAulasRestricaoQuery(aulas: any[]) {
    this.dataService.post('aula/obter-restricoes', aulas).pipe(first()).subscribe(
      next => {
          this.notificacoes = next;
          next.forEach(conflito => {
            conflito.mensagens.forEach(mensagem => {
              let aulas = mensagem.aulas.map(a => Object.assign(a, {tipo: mensagem.tipo}));
              this.aulasConflitantes.push(...aulas);
            })
          });
      }
    )
  }

  getQuantidadeNotificacao(item: any, tipo: number) {
    let mensagens = item.mensagens as any[];
    return mensagens.filter(m => m.tipo == tipo).length;
  }

  getQuantidadeNotificacaoTurma(itemTurma: any, tipo: number) {
    let qtd = 0;
    itemTurma.conflitos.forEach(item => {
      qtd += this.getQuantidadeNotificacao(item, tipo);
    })
    return qtd;
  }

  onValidarAulasClick = () => {
    this.notificacaoTurma = false;
    this.notificacoes = [];
    this.executeAulasRestricaoQuery([...this.aulasVespertinas, ...this.aulasMatutinas, ...this.aulasNoturnas]);
  }

  onControleQuantidadeDisciplinasClick() {
    this.notificacaoTurma = false;
    this.notificacoes = [];
    let aulas = [...this.aulasMatutinas, ...this.aulasVespertinas, ...this.aulasNoturnas];
    if (aulas.length > 0) {
      this.dataService.post('aula/controle-qtd-disciplina', [...this.aulasMatutinas, ...this.aulasVespertinas, ...this.aulasNoturnas]).pipe(first())
        .subscribe(next => {
          if (next.length > 0) {
            this.notificacoes = next;
          }
        })
    }else {
      notify('Ainda não há aulas para esta oferta.', 'error', 2000);
    }
  }

  onSalvarClicked() {
    let aulas = [...this.aulasMatutinas, ...this.aulasVespertinas, ...this.aulasNoturnas];
    if (aulas.length > 0) {
      this.dataService.post('aula/salvar-aulas', aulas).pipe(first()).subscribe(
        next => {
          this.executeOfertaQuery();
          notify('As aulas foram salvas com sucesso!', 'success', 2000);
        },
        catchError( err => {
          notify(err.message, 'error', 2000);
          return of(new Error(err))
        })
      )
    } else {
      notify('Ainda não há aulas para esta oferta.', 'error', 2000);
    }
  }

  private createOferta = () => this.oferta = this.dataService.save('oferta', this.oferta).pipe(first()).subscribe();

  onDesfazerAlteracoesClicked() {
    this.ofertaChanged = false;
    this.executeOfertaQuery();
  }

  onValidarTurmasClick() {
    this.notificacoes = [];
    this.progresso = 0;
    if(this.ofertaChanged) {
      notify('Você possui alterações que precisam ser salvas antes de realizar esta ação!', 'error', 2000);
      return;
    }
    this.notificacaoTurma = true;
    notify('Este processo pode demorar um pouco! Você pode conferir seu progresso na barra de carregamento abaixo!', 'warning', 4000);
    const timer = setInterval(() => {
      this.progresso += 1; // Incrementa o progresso em 5%

      if (this.progresso >= 95) {
        clearInterval(timer); // Interrompe o timer quando o progresso atingir 95%
      }
    }, 200);
    this.validarTurmasSub = this.dataService.asyncPost('aula/validar-turmas/' + this.formGroup.get('ano').value + '/' + this.formGroup.get('semestre').value, null)
      .subscribe((event: HttpEvent<any>) => {
        if (event.type === HttpEventType.DownloadProgress) {
        } else if (event.type === HttpEventType.Response) {
          this.progresso = 100;
          clearInterval(timer);
          this.notificacoes = event.body;
        }
      });
  }
  format = (ratio) => `Progresso: ${parseInt((ratio * 100).toString())}%`;

  ngOnDestroy() {
    this.validarTurmasSub?.unsubscribe();
  }
}
