import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'samha-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {
  /**
   * @param sideBarClicked emite um evento toda vez que o botão 'hamburguer' é clicado, para dar o comportamento
   * de abrir e fechar a SideBar
   */
  @Output() sideBarClicked: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor() { }

  ngOnInit(): void {
  }

  onClick() {
    this.sideBarClicked.emit(null);
  }
}
