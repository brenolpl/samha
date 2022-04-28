import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {SharedModule} from './shared/shared.module';
import {UsuarioFormComponent} from './forms/usuario-form/usuario-form.component';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {AuthGuard} from './guards/auth-guard';
import {AppRoutingModule} from './app-routing.module';
import {CommonModule} from '@angular/common';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    UsuarioFormComponent,
    LoginComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    SharedModule,
    AppRoutingModule,
    BrowserAnimationsModule
  ],
  providers: [
    AuthGuard
  ],
  exports: [
    UsuarioFormComponent,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
