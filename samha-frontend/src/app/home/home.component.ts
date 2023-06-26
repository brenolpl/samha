import {Component, OnInit} from '@angular/core';
import {DataService} from '../shared/service/data.service';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {NotificationService} from "../shared/service/notification.service";

@Component({
  selector: 'samha-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  loading$: Observable<any>;

  columns: any[] = [];

  constructor(private dataService: DataService,
              private notification: NotificationService) {
  }

  ngOnInit(): void {
    this.loading$ = this.dataService.post('menu/list', null).pipe(
      catchError(
        err => {
          this.notification.handleError(err)
          return of(new Error(err));
        }
      )
    );
  }
}
