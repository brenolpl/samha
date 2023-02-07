import {NgModule} from '@angular/core';
import {AlocacaoRoutingModule} from './alocacao-routing.module';
import {AlocacaoComponent} from "./alocacao-edit/alocacao.component";
import {SharedModule} from "../shared/shared.module";
import { AlocacaoFormComponent } from './alocacao-form/alocacao-form.component';
import {MaterialModule} from "../shared/material/material.module";
import {CommonModule} from "@angular/common";


@NgModule({
  declarations: [
    AlocacaoComponent,
    AlocacaoFormComponent,
  ],
  imports: [AlocacaoRoutingModule, SharedModule, MaterialModule, CommonModule]
})
export class AlocacaoModule {}
