import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DataService} from './service/data.service';
import {LocalStorageService} from './service/local-storage.service';
import {ToolbarComponent} from './toolbar/toolbar.component';
import { TableComponent } from './table/table.component';
import {MaterialModule} from './material/material.module';
import { TableDialogComponent } from './table-dialog/table-dialog.component';
import {HttpClientModule} from '@angular/common/http';
import {AuthService} from './service/auth.service';
import {RouterModule} from '@angular/router';

@NgModule({
  declarations: [
    ToolbarComponent,
    TableComponent,
    TableDialogComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule
  ],
  exports: [
    MaterialModule,
    ToolbarComponent,
    TableComponent,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [
    DataService,
    LocalStorageService,
    AuthService
  ],
})
export class SharedModule {
}
