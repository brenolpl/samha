import {NgModule} from '@angular/core';
import {ProfessorFormComponent} from './professor-form/professor-form.component';
import {ProfessorRoutingModule} from './professor-routing.module';
import {SharedModule} from '../shared/shared.module';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [ProfessorFormComponent],
  imports: [
    CommonModule,
    ProfessorRoutingModule,
    SharedModule,
    ReactiveFormsModule
  ],
})
export class ProfessorModule{

}
