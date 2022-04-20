import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LocalStorageService} from './local-storage.service';

@Injectable({providedIn: 'root'})
export class DataService {

  public static APIPREFIX = 'api/';
  private options: any;

  //TODO: localStorage não está sendo injetado no ngInit (delayed)
  constructor(private http: HttpClient,
              private localStorage: LocalStorageService) {
    this.options = {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded')
        .set('Authorization', 'Bearer ' + localStorage.get("access_token"))
    }
  }
  //TODO: Serviço deve passar a retornar a observable do próprio http e não Observable<any>

  public get(resource: string, id: string): Observable<any>{
    return this.http.get(DataService.APIPREFIX + resource + '/' + id, this.options);
  }

  public getAll(resource: string){
    return this.http.get(DataService.APIPREFIX + resource + '/all', this.options);
  }

  public post(resource: string, body: any): Observable<any>{
    return this.http.post(DataService.APIPREFIX + resource, body, this.options);
  }

}
