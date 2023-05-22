import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Observable, of, Subscription} from 'rxjs';
import {MenuEnum} from './shared/menu-enum';
import {alocacaoColumns} from './meta-model/alocacao';
import {TableColumnModel} from './meta-model/table-column-model';
import {DataService} from './shared/service/data.service';
import {LocalStorageService} from './shared/service/local-storage.service';
import {Router} from '@angular/router';
import {AuthService} from './shared/service/auth.service';
import {catchError, map, tap} from 'rxjs/operators';
import {ToastContainerDirective, ToastrService} from "ngx-toastr";
import { locale, loadMessages } from "devextreme/localization";

@Component({
  selector: 'samha-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy{
  @ViewChild(ToastContainerDirective, { static: true }) toastContainer: ToastContainerDirective;
  columns: TableColumnModel[];
  opened = false;
  menus$: Observable<any>;
  showMenu: Observable<boolean>;
  private subscription: Subscription;
  constructor(private dataService: DataService,
              private localStorage: LocalStorageService,
              private router: Router,
              private authService: AuthService,
              private toastrService: ToastrService) {
    locale(navigator.language);
  }

  ngOnInit(): void {
    this.toastrService.overlayContainer = this.toastContainer;
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
        this.router.navigate(['professor']);
        break;
      case MenuEnum.COORDENADORES:
        this.router.navigate(['coordenador']);
        break;
      case MenuEnum.ALOCACOES:
        this.router.navigate(['alocacao']);
        break;
      case MenuEnum.DISCIPLINAS:
        this.router.navigate(['disciplina']);
        break;
      case MenuEnum.CADASTRARUSUARIOS:
        this.router.navigate(['usuario']);
        break;
      case MenuEnum.CURSO:
        this.router.navigate(['curso']);
        break;
      case MenuEnum.OFERTAS:
        this.opened = false;
        this.router.navigate(['oferta']);
        break;
      case MenuEnum.RELATORIOS:
        this.columns = [];
        break;
      case MenuEnum.TURMAS:
        this.router.navigate(['turma']);
        break;
      case MenuEnum.COORDENADORIA:
        this.router.navigate(['coordenadoria']);
        break;
      case MenuEnum.EIXO:
        this.router.navigate(['eixo']);
        break;
      case MenuEnum.MATRIZ:
        this.router.navigate(['matrizCurricular']);
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
        return 'group_work';
      case MenuEnum.COORDENADORIA:
        return 'layers'
      case MenuEnum.EIXO:
        return '360';
      case MenuEnum.MATRIZ:
        return 'grain';
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}

export const APIPREFIX = 'api/';
