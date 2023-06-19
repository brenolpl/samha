import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SharedModule} from "../shared/shared.module";
import {DxButtonModule, DxPopupModule, DxTabPanelModule, DxTabsModule} from "devextreme-angular";
import {NgModule} from "@angular/core";
import {RelatorioRoutingModule} from "./relatorio-routing.module";
import {RelatorioComponent} from "./relatorio/relatorio.component";
import {MatToolbarModule} from "@angular/material/toolbar";
import { RelatorioProfessorComponent } from './relatorio-professor/relatorio-professor.component';
import {MatRadioModule} from "@angular/material/radio";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {MaterialModule} from "../shared/material/material.module";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { RelatorioTurmaComponent } from './relatorio-turma/relatorio-turma.component';
import { ConfirmGenerationPopupComponent } from './confirm-generation-popup/confirm-generation-popup.component';
import { ReportAulaGridComponent } from './report-aula-grid/report-aula-grid.component';

@NgModule({
    declarations: [RelatorioComponent, RelatorioProfessorComponent, RelatorioTurmaComponent, ConfirmGenerationPopupComponent, ReportAulaGridComponent],
  imports: [
    CommonModule,
    FormsModule,
    RelatorioRoutingModule,
    ReactiveFormsModule,
    SharedModule,
    DxButtonModule,
    MatToolbarModule,
    DxTabPanelModule,
    DxTabsModule,
    MaterialModule,
    MatRadioModule,
    DxPopupModule,
    FontAwesomeModule
  ],
    exports: []
})
export class ProfessorModule{

}
