import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {HttpEvent, HttpEventType} from "@angular/common/http";
import {FunctionHelper} from "../../shared/function-helper";
import {Subscription} from "rxjs";
import {DataService} from "../../shared/service/data.service";
import {RelatorioDto} from "../../meta-model/relatorio-professor";
import {NotificationService} from "../../shared/service/notification.service";

@Component({
  selector: 'samha-relatorio',
  templateUrl: './relatorio.component.html',
  styleUrls: ['./relatorio.component.css']
})
export class RelatorioComponent implements OnInit, OnDestroy {
  public semestreControl = new FormControl(new Date().getMonth() < 6 ? 1 : 2);
  public anoControl = new FormControl(new Date().getFullYear());
  public showPopup: boolean = false;

  public tabs = [
    {
      text: 'Professores',
      id: 1
    },
    {
      text: 'Turmas',
      id: 2
    }
  ];
  public selectedTab: number = 1;
  public isGenerating: boolean = false;
  private gerarPdfSub: Subscription;

  constructor(private dataService: DataService,
              private notification: NotificationService) { }

  ngOnInit(): void {
  }

  selectTab = (e: any) => this.selectedTab = e.itemIndex;

  gerarRelatorioDisciplinas() {
    this.showPopup = true;
  }

  onGerarClick() {
    this.isGenerating = true;
    this.showPopup = false;
    this.gerarPdfSub = this.dataService.asyncPost('disciplina/gerar-relatorio', this.getRelatorioDto())
      .subscribe((event: HttpEvent<any>) => {
        if (event.type === HttpEventType.DownloadProgress) {
        } else if (event.type === HttpEventType.Response) {
          FunctionHelper.downloadFile("relatorios_disciplinas.zip", event.body.bytes);
          this.notification.success('Relatório gerado com sucesso!');
          this.isGenerating = false;
        }
      }, error => {
        this.isGenerating = false;
        this.notification.handleError(error)
      });
  }


  onCancelarClick() {
    this.showPopup = false;
  }

  ngOnDestroy() {
    this.gerarPdfSub?.unsubscribe();
  }

  private getRelatorioDto(): RelatorioDto {
    return {
      ano: this.anoControl.value,
      semestre: this.semestreControl.value
    }
  }
}
