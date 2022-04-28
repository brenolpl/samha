import {EventEmitter, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {APIPREFIX} from '../../app.component';
import {Observable, of} from 'rxjs';
import {LocalStorageService} from './local-storage.service';

@Injectable()
export class AuthService{

  loggedIn: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(private http: HttpClient,
              private localStorage: LocalStorageService) {
  }

  public login(body: any){
    return this.http.post(APIPREFIX + 'login', body, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded')
    });
  }

  public isTokenValid(): Observable<any>{
    return this.http.post(APIPREFIX + 'auth/isTokenValid', null, this.getOptions());
  }

  private getOptions() {
    return {
      headers: new HttpHeaders()
        .set('Authorization', 'Bearer ' + this.localStorage.get('access_token'))
    }
  }
}
