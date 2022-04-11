import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class DataService {

  private static APIPREFIX = 'api/';

  constructor(private http: HttpClient) {}

  public get(resource: string, id: string): Observable<any>{
    return this.http.get(DataService.APIPREFIX + resource + '/' + id);
  }

  public post(resource: string, body: any): Observable<any>{
    return this.http.post(DataService.APIPREFIX + resource, body);
  }

}
