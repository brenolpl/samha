import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {SharedModule} from './shared/shared.module';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {UsuarioFormComponent} from './forms/usuario-form/usuario-form.component';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import { ProfessorFormComponent } from './forms/professor-form/professor-form.component';

@NgModule({
  declarations: [
    AppComponent,
    UsuarioFormComponent,
    LoginComponent,
    HomeComponent,
    ProfessorFormComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    SharedModule,
    RouterModule
  ],
  providers: [],
  exports: [
    UsuarioFormComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
