import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SamhaFormComponent} from './samha-form/samha-form.component';
import {FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';

@NgModule({
  declarations: [
    SamhaFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class SharedModule { }
