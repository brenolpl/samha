import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SamhaFormComponent} from './samha-form/samha-form.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DataService} from './data.service';
import {AuthService} from './auth.service';
import {AngularFireModule} from '@angular/fire/compat';
import {environment} from '../../environments/environment';
import {AngularFirestoreModule} from '@angular/fire/compat/firestore';
import {LocalStorageService} from './local-storage.service';

@NgModule({
  declarations: [
    SamhaFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFirestoreModule
  ],
  providers: [
    DataService,
    AuthService,
    LocalStorageService
  ]
})
export class SharedModule { }
