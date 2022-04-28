import {Component, EventEmitter, OnDestroy, OnInit} from '@angular/core';
import {Observable, of, Subscription} from 'rxjs';
import {MenuEnum} from './shared/menu-enum';
import {professorColumns} from './meta-model/professor';
import {alocacaoColumns} from './meta-model/alocacao';
import {disciplinaColumns} from './meta-model/disciplina';
import {cursoColumns} from './meta-model/curso';
import {turmaColumns} from './meta-model/turma';
import {TableColumnModel} from './meta-model/table-column-model';
import {DataService} from './shared/service/data.service';
import {LocalStorageService} from './shared/service/local-storage.service';
import {Router} from '@angular/router';
import {AuthService} from './shared/service/auth.service';
import {catchError, map, tap} from 'rxjs/operators';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'samha-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy{
  columns: TableColumnModel[];
  selectedMenu: number;
  opened = false;
  menus$: Observable<any>;
  showMenu: Observable<boolean>;
  private subscription: Subscription;
  constructor(private dataService: DataService,
              private localStorage: LocalStorageService,
              private router: Router,
              private authService: AuthService) {

  }

  ngOnInit(): void {
    this.showMenu = this.authService.isTokenValid().pipe(
      map( _ => {
        this.opened = true;
        return true;
      }),
      tap(
        _ => this.menus$ = this.dataService.post('menu/list', null)
      ),
      catchError(
        _ => {
          this.opened = false;
          this.menus$ = of([]);
          this.subscription = this.authService.loggedIn.subscribe(
            next => {
              if(next) this.ngOnInit();
            },
            error => {
              throw error;
            }
          )
          return of(false);
        }
      )
    );
  }

  onSideBarClicked() {
    this.opened = !this.opened;
  }

  onMenuClick(menu: number) {
    switch (menu) {
      case MenuEnum.PROFESSORES:
        this.router.navigate(['professor'], {state: {
            resource: 'professor',
            columns: professorColumns
          }});
        break;
      case MenuEnum.COORDENADORES:
        this.columns = professorColumns;
        break;
      case MenuEnum.ALOCACOES:
        this.columns = alocacaoColumns;
        break;
      case MenuEnum.DISCIPLINAS:
        this.columns = disciplinaColumns;
        break;
      case MenuEnum.CADASTRARUSUARIOS:
        this.columns = [];
        break;
      case MenuEnum.CURSO:
        this.columns = cursoColumns;
        break;
      case MenuEnum.OFERTAS:
        this.columns = [];
        break;
      case MenuEnum.RELATORIOS:
        this.columns = [];
        break;
      case MenuEnum.TURMAS:
        this.columns = turmaColumns;
        break;
    }
  }

  loadMatIcon(menu: number) {
    switch (menu) {
      case MenuEnum.PROFESSORES:
        return 'person';
      case MenuEnum.COORDENADORES:
        return 'person_pin';
      case MenuEnum.ALOCACOES:
        return 'link';
      case MenuEnum.DISCIPLINAS:
        return 'library_books';
      case MenuEnum.CADASTRARUSUARIOS:
        return 'group_add';
      case MenuEnum.CURSO:
        return 'school';
      case MenuEnum.OFERTAS:
        return 'grid_on';
      case MenuEnum.RELATORIOS:
        return 'picture_as_pdf';
      case MenuEnum.TURMAS:
        return 'group_work'
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}

export const APIPREFIX = 'api/';
