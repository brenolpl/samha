import {Component, OnInit} from '@angular/core';
import {DataService} from '../shared/data.service';
import {MenuEnum} from '../shared/menu-enum';
import {usuarioColumns} from '../meta-model/usuario';
import {alocacaoColumns} from '../meta-model/alocacao';
import {disciplina} from '../meta-model/disciplina';

@Component({
  selector: 'samha-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  opened = true;
  menusPermitidos: any[];
  coordenadores: boolean;
  professores:boolean;
  alocacoes: boolean;
  selectedMenu: MenuEnum;

  columns: any[] = [];

  constructor(private dataService: DataService) {
  }

  ngOnInit(): void {
    this.dataService.post('menu/list', null).subscribe(
      (result) => {
        this.menusPermitidos = result;
        this.loadMenus();
      },
      (error) => {
        throw error;
      }
    )
  }

  testButton($event: MouseEvent) {

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
            // this.loadProfessores();
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
    this.columns = usuarioColumns;
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
