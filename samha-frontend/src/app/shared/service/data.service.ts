import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LocalStorageService} from './local-storage.service';
import {QueryMirror} from '../query-mirror';
import {PagedList} from '../paged-list';

@Injectable({providedIn: 'root'})
export class DataService {

  public static APIPREFIX = 'api/';
  private options: any;

  constructor(private http: HttpClient,
              private localStorage: LocalStorageService) {
    this.options = {
      headers: new HttpHeaders()
        .set('Authorization', 'Bearer ' + this.localStorage.get("access_token"))
    }
  }
  //TODO: Serviço deve passar a retornar a observable do próprio http e não Observable<any>

  public query(query: QueryMirror): Observable<HttpEvent<PagedList>> {
    return this.http.post<PagedList>(DataService.APIPREFIX + query.entityPath + '/query', query, this.options);
  }

  public get(resource: string, id: string): Observable<any>{
    return this.http.get(DataService.APIPREFIX + resource + '/' + id, this.options);
  }

  public getAll(resource: string): Observable<any>{
    return this.http.get(DataService.APIPREFIX + resource + '/all', this.options);
  }

  public post(resource: string, body: any): Observable<any>{
    return this.http.post(DataService.APIPREFIX + resource, body, this.options);
  }

  public login(body: any){
    return this.http.post(DataService.APIPREFIX + 'login', body, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded')
    })

  }

}
