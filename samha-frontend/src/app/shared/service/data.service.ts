import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LocalStorageService} from './local-storage.service';
import {QueryMirror} from '../query-mirror';
import {PagedList} from '../paged-list';

@Injectable({providedIn: 'root'})
export class DataService {

  public static readonly APIPREFIX = 'api/';

  constructor(private http: HttpClient,
              private localStorage: LocalStorageService) {
  }

  public query(query: QueryMirror): Observable<PagedList> {
    return this.http.post<PagedList>(DataService.APIPREFIX + query.entityPath + '/query', query, this.getOptions());
  }

  public get(resource: string, id: string): Observable<any>{
    return this.http.get(DataService.APIPREFIX + resource + '/' + id, this.getOptions());
  }

  public getAll(resource: string): Observable<any>{
    return this.http.get(DataService.APIPREFIX + resource + '/all', this.getOptions());
  }

  public post(resource: string, body: any): Observable<any>{
    return this.http.post(DataService.APIPREFIX + resource, body, this.getOptions());
  }

  public login(body: any){
    return this.http.post(DataService.APIPREFIX + 'login', body, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded')
    })

  }

  private getOptions() {
    return {
      headers: new HttpHeaders()
        .set('Authorization', 'Bearer ' + this.localStorage.get('access_token'))
    }
  }

}
