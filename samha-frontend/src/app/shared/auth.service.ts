import {Injectable} from '@angular/core';
import {EMPTY, Observable} from 'rxjs';
import firebase from 'firebase/compat';
import {AngularFireAuth} from '@angular/fire/compat/auth';
import {LocalStorageService} from './local-storage.service';

@Injectable()
export class AuthService {
  userData: Observable<firebase.User>;

  constructor(private fireAuth: AngularFireAuth,
              private localStorage: LocalStorageService) {
  }

  signUp(email: string, password: string): void{
    this.fireAuth.createUserWithEmailAndPassword(email, password).then(
      (result) => {
        console.log(result);
      }
    ).catch(
      (error) => {
        throw error;
      }
    );
  }

  signIn(email: string, password: string): Observable<any>{
    this.fireAuth.signInWithEmailAndPassword(email, password).then(
      (result) => {
        console.log(result);
        this.localStorage.set('currentUserId', result.user.uid);
      }
    ).catch(
      (error) => {
        throw error;
      }
    );
    return EMPTY;
  }
}
