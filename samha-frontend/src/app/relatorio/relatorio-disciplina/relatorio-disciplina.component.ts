import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {Subscription} from "rxjs";
import {DataService} from "../../shared/service/data.service";
import {NotificationService} from "../../shared/service/notification.service";
import {HttpEvent, HttpEventType} from "@angular/common/http";
import {FunctionHelper} from "../../shared/function-helper";
import {RelatorioDto} from "../../meta-model/relatorio-professor";
import {AuthService} from "../../shared/service/auth.service";

@Component({
  selector: 'samha-relatorio-disciplina',
  templateUrl: './relatorio-disciplina.component.html',
  styleUrls: ['./relatorio-disciplina.component.css']
})
export class RelatorioDisciplinaComponent implements OnInit, OnDestroy {
  @Input() public semestreControl: FormControl;
  @Input() public anoControl: FormControl;
  public isGenerating: boolean = false;
  private gerarPdfSub: Subscription;
  constructor(private dataService: DataService,
              private notification: NotificationService,
              private authService: AuthService) { }

  ngOnInit(): void {
  }

  onGerarClick() {
    this.isGenerating = true;
    this.gerarPdfSub = this.dataService.publicAsyncPost('relatorio/gerar-relatorio-disciplina', this.getRelatorioDto())
      .subscribe((event: HttpEvent<any>) => {
        if (event.type === HttpEventType.DownloadProgress) {
        } else if (event.type === HttpEventType.Response) {
          FunctionHelper.downloadFile(event.body.nomeArquivo, event.body.bytes);
          this.notification.success('Relatório gerado com sucesso!');
          this.isGenerating = false;
        }
      }, error => {
        this.isGenerating = false;
        this.notification.handleError(error)
      });
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

  onAnoSemestreChange() {
    this.authService.loggedIn
  }
}
