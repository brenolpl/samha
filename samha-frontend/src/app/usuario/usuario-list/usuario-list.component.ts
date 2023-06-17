import { Component, OnInit } from '@angular/core';
import {usuarioColumns} from "../../meta-model/usuario";

@Component({
  selector: 'samha-usuario-list',
  templateUrl: './usuario-list.component.html',
  styleUrls: ['./usuario-list.component.css']
})
export class UsuarioListComponent implements OnInit {
  public readonly usuarioColumns = usuarioColumns;
  constructor() { }

  ngOnInit(): void {
  }

  goBack() {

  }


}
