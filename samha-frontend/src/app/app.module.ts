import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {LoginComponent} from './login-component/login/login.component';
import {HttpClientModule} from '@angular/common/http';
import {SharedModule} from './shared/shared.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AngularFireModule} from '@angular/fire/compat';
import {environment} from '../environments/environment';
import {AngularFirestoreModule} from '@angular/fire/compat/firestore';

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
        ReactiveFormsModule,
        AngularFireModule.initializeApp(environment.firebaseConfig),
        AngularFirestoreModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
