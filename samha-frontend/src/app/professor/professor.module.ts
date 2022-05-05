import {NgModule} from '@angular/core';
import {ProfessorFormComponent} from './professor-form/professor-form.component';
import {ProfessorRoutingModule} from './professor-routing.module';
import {SharedModule} from '../shared/shared.module';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { ProfessorDetailComponent } from './professor-detail/professor-detail.component';
import { RestricaoComponent } from './restricao/restricao.component';
import { RestricaoListComponent } from './restricao/restricao-list/restricao-list.component';

@NgModule({
  declarations: [ProfessorFormComponent, ProfessorDetailComponent, RestricaoComponent, RestricaoListComponent],
  imports: [
    CommonModule,
    ProfessorRoutingModule,
    FormsModule,
    SharedModule,
    ReactiveFormsModule
  ],
})
export class ProfessorModule{

}
