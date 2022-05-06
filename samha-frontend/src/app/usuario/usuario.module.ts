import {NgModule} from '@angular/core';
import {UsuarioFormComponent} from './usuario-form/usuario-form.component';
import {UsuarioRoutingModule} from './usuario-routing.module';
import {MaterialModule} from '../shared/material/material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';


@NgModule({
  declarations: [UsuarioFormComponent],
  imports: [
    UsuarioRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    CommonModule
  ],
  exports: []
})
export class UsuarioModule {}
