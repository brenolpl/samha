import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {Observable, Subscription} from "rxjs";
import {MatSelectChange} from "@angular/material/select";
import {PagedList} from "../../shared/paged-list";
import {DataService} from "../../shared/service/data.service";
import {NotificationService} from "../../shared/service/notification.service";
import {QueryMirror} from "../../shared/query-mirror";
import {RelatorioDto} from "../../meta-model/relatorio-professor";
import {HttpEvent, HttpEventType} from "@angular/common/http";
import {FunctionHelper} from "../../shared/function-helper";
import {AuthService} from "../../shared/service/auth.service";

@Component({
  selector: 'samha-relatorio-turma',
  templateUrl: './relatorio-turma.component.html',
  styleUrls: ['./relatorio-turma.component.css']
})
export class RelatorioTurmaComponent implements OnInit, OnDestroy {
  @Input() public semestreControl: FormControl;
  @Input() public anoControl: FormControl;
  public compareFunction = (o1: any, o2: any) => (o1 != null && o2 != null && o1.id == o2.id);
  public radioGroupControl = new FormControl(1);
  public eixos$: Observable<any>;
  public eixoControl = new FormControl({});
  public cursoControl = new FormControl({});
  public turmaControl = new FormControl({});
  public curso$: Observable<PagedList>;
  public turma$: Observable<PagedList>;
  public turnoControl = new FormControl('MATUTINO');
  public aulasTurma$: Observable<any>;
  public isGenerating: boolean = false;
  public showPopup: boolean = false;
  private gerarPdfSub: Subscription;


  constructor(private dataService: DataService,
              private notification: NotificationService,
              private authService: AuthService) {}

  ngOnInit(): void {
    this.eixos$ = this.dataService.publicGetAll('eixo');
  }

  onRadioSelected() {
    const selected = this.radioGroupControl.value;
    switch (selected) {
      case '1':
        this.eixoControl.setValue({});
        this.cursoControl.setValue({});
        this.turmaControl.setValue({});
        break;
      case '2':
        this.cursoControl.setValue({});
        this.turmaControl.setValue({});
        break;
      case '3':
        this.turmaControl.setValue({});
        if (this.cursoControl.value?.id) this.onAnoSemestreChange();
        break;
      case '4':
        if (this.turmaControl.value?.id) this.onAnoSemestreChange();
        break;
    }
  }

  onEixoSelected(eixo: MatSelectChange) {
      this.curso$ = this.dataService.publicQuery(new QueryMirror('curso').selectList(['id', 'nome']).where({
        and: {
          'coordenadoria.eixo.id': {equals: eixo.value.id}
        }
      }))
  }

  onCursoChange(curso: MatSelectChange) {
    this.turma$ = this.dataService.publicQuery(new QueryMirror('turma').selectList(['id', 'nome']).where({
      and: {
        'matriz.curso.id': {equals: curso.value.id},
        'ativa': {equals: true}
      }
    }))
    this.onAnoSemestreChange();
  }

  onAnoSemestreChange() {
    if (this.anoControl.valid && this.semestreControl.valid) {
      if (this.cursoControl.value?.id) this.aulasTurma$ = this.dataService.publicPost('relatorio/obter-aulas-turma', this.getRelatorioDto())
    }
  }

  private getRelatorioDto(): RelatorioDto {
    return {
      ano: this.anoControl.value,
      semestre: this.semestreControl.value,
      eixoId: this.eixoControl.value?.id,
      cursoId: this.cursoControl.value?.id,
      turmaId: this.turmaControl.value?.id,
      nomeRelatorio: 'relatorioGenerico'
    }
  }

  generatePdf() {
    let cursoId = this.cursoControl.value?.id;
    if (cursoId == undefined) {
      this.showPopup = true;
    } else {
      this.gerarRelatorio();
    }
  }

  private gerarRelatorio() {
    this.isGenerating = true;
    this.gerarPdfSub = this.dataService.publicAsyncPost('relatorio/gerar-relatorio-turma', this.getRelatorioDto())
      .subscribe((event: HttpEvent<any>) => {
        if (event.type === HttpEventType.DownloadProgress) {
        } else if (event.type === HttpEventType.Response) {
          console.log(event.body);
          FunctionHelper.downloadFile(event.body.nomeArquivo, event.body.bytes);
          this.notification.success('Relatório gerado com sucesso!');
          this.isGenerating = false;
        }
      }, error => {
        this.isGenerating = false;
        this.notification.handleError(error)
      });
  }

  onOptionChoosen($event: boolean) {
    if ($event) this.gerarRelatorio();
    this.showPopup = false;
  }

  ngOnDestroy() {
    this.gerarPdfSub?.unsubscribe();
  }
}
