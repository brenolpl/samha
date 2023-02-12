import {NgModule} from '@angular/core';
import {AlocacaoRoutingModule} from './alocacao-routing.module';
import {AlocacaoComponent} from "./alocacao-edit/alocacao.component";
import {SharedModule} from "../shared/shared.module";
import { AlocacaoFormComponent } from './alocacao-form/alocacao-form.component';
import {MaterialModule} from "../shared/material/material.module";
import {CommonModule} from "@angular/common";
import { AlocacaoMainComponent } from './alocacao-main/alocacao-main.component';


@NgModule({
  declarations: [
    AlocacaoComponent,
    AlocacaoFormComponent,
    AlocacaoMainComponent,
  ],
  imports: [AlocacaoRoutingModule, SharedModule, MaterialModule, CommonModule]
})
export class AlocacaoModule {}
