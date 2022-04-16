import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SamhaFormComponent} from './samha-form/samha-form.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DataService} from './data.service';
import {LocalStorageService} from './local-storage.service';

@NgModule({
  declarations: [
    SamhaFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [
    DataService,
    LocalStorageService
  ]
})
export class SharedModule { }
