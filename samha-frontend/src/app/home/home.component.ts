import {Component, OnInit} from '@angular/core';
import {DataService} from '../shared/service/data.service';
import {MenuEnum} from '../shared/menu-enum';
import {alocacaoColumns} from '../meta-model/alocacao';
import {disciplinaColumns} from '../meta-model/disciplina';
import {Observable, of} from 'rxjs';
import {professorColumns} from '../meta-model/professor';
import {catchError} from 'rxjs/operators';
import {Menu} from '../meta-model/menu';
import {cursoColumns} from '../meta-model/curso';
import {turmaColumns} from '../meta-model/turma';
import {NotificationService} from "../shared/service/notification.service";

@Component({
  selector: 'samha-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  loading$: Observable<any>;
  opened = true;
  selectedMenu: MenuEnum;

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
