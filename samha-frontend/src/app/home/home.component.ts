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

@Component({
  selector: 'samha-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  loading$: Observable<any>;
  opened = true;
  menusPermitidos: Menu[];
  coordenadores: boolean = false;
  professores: boolean = false;
  alocacoes: boolean = false;
  disciplinas: boolean = false;
  cadastrarUsuario: boolean = false;
  selectedMenu: MenuEnum;

  columns: any[] = [];

  constructor(private dataService: DataService) {
  }

  ngOnInit(): void {
    this.loading$ = this.dataService.post('menu/list', null).pipe(
      // tap(
      //   next => {
      //     this.menusPermitidos = next;
      //     this.loadMenus();
      //   }
      // ),
      catchError(
        () => of([])
      )
    );
  }


  onSideBarClicked() {
    this.opened = !this.opened;
  }

  onMenuClick(menu: number) {
    switch (menu) {
      case MenuEnum.PROFESSORES:
        this.columns = professorColumns;
        this.selectedMenu = MenuEnum.PROFESSORES;
        break;
      case MenuEnum.COORDENADORES:
        this.columns = professorColumns;
        this.selectedMenu = MenuEnum.COORDENADORES;
        break;
      case MenuEnum.ALOCACOES:
        this.columns = alocacaoColumns;
        this.selectedMenu = MenuEnum.ALOCACOES;
        break;
      case MenuEnum.DISCIPLINAS:
        this.columns = disciplinaColumns;
        this.selectedMenu = MenuEnum.DISCIPLINAS;
        break;
      case MenuEnum.CADASTRARUSUARIOS:
        this.columns = [];
        this.selectedMenu = MenuEnum.CADASTRARUSUARIOS;
        break;
      case MenuEnum.CURSO:
        this.columns = cursoColumns;
        this.selectedMenu = MenuEnum.CURSO;
        break;
      case MenuEnum.OFERTAS:
        this.columns = [];
        this.selectedMenu = 0;
        break;
      case MenuEnum.RELATORIOS:
        this.columns = [];
        this.selectedMenu = 0;
        break;
      case MenuEnum.TURMAS:
        this.columns = turmaColumns;
        this.selectedMenu = MenuEnum.TURMAS;
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
}
