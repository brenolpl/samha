import {NgModule} from '@angular/core';
import {CoordenadoriaRoutingModule} from './coordenadoria-routing.module';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SharedModule} from '../shared/shared.module';
import {MaterialModule} from '../shared/material/material.module';
import {CoordenadoriaFormComponent} from './coordenadoria-form/coordenadoria-form.component';

@NgModule({
  declarations:[
    CoordenadoriaFormComponent
  ],
  imports: [
    CoordenadoriaRoutingModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    MaterialModule
  ]
})
export class CoordenadoriaModule {}
