import { Component, OnInit } from '@angular/core';
import {DataService} from '../shared/data.service';
import {MenuEnum} from '../shared/menu-enum';
import {ProfessorModel} from '../meta-model/professor-model';

@Component({
  selector: 'samha-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  opened = false;
  menusPermitidos: number[];
  professoresArray: ProfessorModel[];

  professores: boolean;
  coordenadores: boolean;
  alocacoes: boolean;
  username: string = 'Teste';

  constructor(private dataService: DataService) { }

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

  private loadProfessores(){
    this.dataService.getAll('professor/all').subscribe(
      (result: ProfessorModel[]) => {
        this.professoresArray = result;
      },
      (error) => {
        throw error;
      }
    )
  }

  onSideBarClicked() {
    this.opened = ! this.opened;
  }
}
