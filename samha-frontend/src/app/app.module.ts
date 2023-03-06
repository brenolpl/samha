import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {SharedModule} from './shared/shared.module';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {AuthGuard} from './guards/auth-guard';
import {AppRoutingModule} from './app-routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CanDeactivateGuard} from './guards/can-deactivate-guard';
import {FormResolver} from './guards/form-resolver';
import {MaterialModule} from './shared/material/material.module';
import {ToastContainerModule, ToastrModule} from "ngx-toastr";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent
  ],
  imports: [
    MaterialModule,
    BrowserModule,
    SharedModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      positionClass: 'toast-bottom-right',
      progressBar: true,
      tapToDismiss: true,
      progressAnimation: 'decreasing',
      newestOnTop: false,
      easing: 'ease-in',
      closeButton: true,
    }),
    ToastContainerModule
  ],
  providers: [
    AuthGuard,
    CanDeactivateGuard,
    FormResolver
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
