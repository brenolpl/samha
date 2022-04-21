import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DataService} from './service/data.service';
import {LocalStorageService} from './service/local-storage.service';
import {ToolbarComponent} from './toolbar/toolbar.component';
import {AppRoutingModule} from '../app-routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LoginComponent} from '../login/login.component';
import {HomeComponent} from '../home/home.component';
import { TableComponent } from './table/table.component';
import {MaterialModule} from './material/material.module';

@NgModule({
  declarations: [
    LoginComponent,
    HomeComponent,
    ToolbarComponent,
    TableComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  exports: [
  ],
  providers: [
    DataService,
    LocalStorageService
  ]
})
export class SharedModule {
}
