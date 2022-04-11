import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LoginComponent } from './login-component/login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {DataService} from './shared/data.service';
import {SharedModule} from './shared/shared.module';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
  ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        SharedModule,
        ReactiveFormsModule
    ],
  providers: [DataService],
  bootstrap: [AppComponent]
})
export class AppModule { }
