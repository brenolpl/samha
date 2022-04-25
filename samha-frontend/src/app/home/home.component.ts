import {Component, OnInit} from '@angular/core';
import {DataService} from '../shared/service/data.service';
import {MenuEnum} from '../shared/menu-enum';
import {alocacaoColumns} from '../meta-model/alocacao';
import {disciplina} from '../meta-model/disciplina';
import {Observable, of} from 'rxjs';
import {professorColumns} from '../meta-model/professor';
import {catchError, tap} from 'rxjs/operators';

@Component({
  selector: 'samha-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  loading$: Observable<any>;
  opened = true;
  menusPermitidos: any[];
  coordenadores: boolean;
  professores: boolean;
  alocacoes: boolean;
  selectedMenu: MenuEnum;

  columns: any[] = [];

  constructor(private dataService: DataService) {
  }

  ngOnInit(): void {
    this.loading$ = this.dataService.post('menu/list', null).pipe(
      tap(
        next => {
          this.menusPermitidos = next;
          this.loadMenus();
        }
      ),
      catchError(
        () => of([])
      )
    );
  }

  private loadMenus() {
    this.menusPermitidos.map(
      index => {
        switch (index) {
          case MenuEnum.COORDENADORES:
            this.coordenadores = true;
            break;
          case MenuEnum.PROFESSORES:
            this.professores = true;
            break;
          case MenuEnum.ALOCACOES:
            this.alocacoes = true;
            break;
        }
      }
    )
  }

  onSideBarClicked() {
    this.opened = !this.opened;
  }

  onBotaoProfessorClick() {
    this.columns = professorColumns;
    this.selectedMenu = MenuEnum.PROFESSORES;
  }

  onBotaoCoordenadoresClick() {
    this.selectedMenu = MenuEnum.COORDENADORES;
  }

  onBotaoAlocacaoClick() {
    this.columns = alocacaoColumns;
    this.selectedMenu = MenuEnum.ALOCACOES
  }

  onBotaoDisciplinaClick() {
    this.columns = disciplina;
    this.selectedMenu = MenuEnum.DISCIPLINA
  }

}
