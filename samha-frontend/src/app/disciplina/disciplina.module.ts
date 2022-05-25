import {NgModule} from '@angular/core';
import {DisciplinaRoutingModule} from './disciplina-routing.module';
import {MaterialModule} from '../shared/material/material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {SharedModule} from '../shared/shared.module';
import {DisciplinaFormComponent} from './disciplina-form/disciplina-form.component';
import { DisciplinaComponent } from './disciplina/disciplina.component';

@NgModule({
  imports: [
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    DisciplinaRoutingModule,
    CommonModule,
    SharedModule,

  ],
  declarations: [
    DisciplinaFormComponent,
    DisciplinaComponent
  ]
})
export class DisciplinaModule {}
