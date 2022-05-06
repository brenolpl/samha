import {NgModule} from '@angular/core';
import {EixoRoutingModule} from './eixo-routing.module';
import { EixoFormComponent } from './eixo-form/eixo-form.component';
import {MaterialModule} from '../shared/material/material.module';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';


@NgModule({
  declarations: [
    EixoFormComponent
  ],
  imports: [
    EixoRoutingModule,
    MaterialModule,
    CommonModule,
    ReactiveFormsModule
  ]
})
export class EixoModule {}
