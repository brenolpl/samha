import { Component, OnInit } from '@angular/core';
import {DataService} from '../shared/data.service';
import {MenuEnum} from '../shared/menu-enum';

@Component({
  selector: 'samha-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  menusPermitidos: number[];

  professores: boolean;
  coordenadores: boolean;
  alocacoes: boolean;

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
            break;
          case MenuEnum.ALOCACOES:
            this.alocacoes = true;
            break;
        }
      }
    )
  }
}
