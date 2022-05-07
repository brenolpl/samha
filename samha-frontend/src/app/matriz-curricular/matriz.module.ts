import {NgModule} from '@angular/core';
import {MatrizRoutingModule} from './matriz-routing.module';
import {MaterialModule} from '../shared/material/material.module';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatrizFormComponent} from "./matriz-form/matriz-form.component";
import {SharedModule} from "../shared/shared.module";


@NgModule({
  declarations: [
    MatrizFormComponent
  ],
  imports: [
    MatrizRoutingModule,
    MaterialModule,
    CommonModule,
    ReactiveFormsModule,
    SharedModule
  ]
})
export class MatrizModule {}
