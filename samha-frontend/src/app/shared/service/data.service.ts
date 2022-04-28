import {EventEmitter, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LocalStorageService} from './local-storage.service';
import {QueryMirror} from '../query-mirror';
import {PagedList} from '../paged-list';
import {catchError, tap} from 'rxjs/operators';
import {APIPREFIX} from '../../app.component';

@Injectable({providedIn: 'root'})
export class DataService {

  showMenu: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(private http: HttpClient,
              private localStorage: LocalStorageService) {
  }

  public query(query: QueryMirror): Observable<PagedList> {
    return this.http.post<PagedList>(APIPREFIX + query.entityPath + '/query', query, this.getOptions());
  }

  public get(resource: string, id: string): Observable<any>{
    return this.http.get(APIPREFIX + resource + '/' + id, this.getOptions());
  }

  public getAll(resource: string): Observable<any>{
    return this.http.get(APIPREFIX + resource + '/all', this.getOptions());
  }

  public post(resource: string, body: any): Observable<any>{
    return this.http.post(APIPREFIX + resource, body, this.getOptions());
  }

  public save(resource: string, body: any): Observable<any>{
    return this.http.post(APIPREFIX + resource + '/insert', body, this.getOptions());
  }

  private getOptions() {
    return {
      headers: new HttpHeaders()
        .set('Authorization', 'Bearer ' + this.localStorage.get('access_token'))
    }
  }

}
