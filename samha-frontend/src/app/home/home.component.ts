import {Component, OnDestroy, OnInit} from '@angular/core';
import {DataService} from '../shared/service/data.service';
import {MenuEnum} from '../shared/menu-enum';
import {alocacaoColumns} from '../meta-model/alocacao';
import {disciplina} from '../meta-model/disciplina';
import {Subscription} from 'rxjs';
import {LocalStorageService} from '../shared/service/local-storage.service';
import {professorColumns} from '../meta-model/professor';

@Component({
  selector: 'samha-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy{
  isloading: boolean = true;
  private subscription: Subscription;
  opened = true;
  menusPermitidos: any[];
  coordenadores: boolean;
  professores:boolean;
  alocacoes: boolean;
  selectedMenu: MenuEnum;

  columns: any[] = [];

  constructor(private dataService: DataService,
              private localStorage: LocalStorageService) {
  }

  ngOnInit(): void {
    this.loadData();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  private loadMenus() {
    this.menusPermitidos.map(
      index => {
        switch (index){
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
    this.opened = ! this.opened;
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

  private loadData() {
    if(this.localStorage.get('access_token')) {
      this.subscription = this.dataService.post('menu/list', null).subscribe(
        (result) => {
          this.menusPermitidos = result;
          this.loadMenus();
          this.isloading = false;
        },
        (error) => {
          throw error;
        }
      );
    }else{
      this.loadData();
    }
  }
}
