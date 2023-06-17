import { Component, OnInit } from '@angular/core';
import {coordenadorColumns} from "../../meta-model/servidor";

@Component({
  selector: 'samha-coordenador-list',
  templateUrl: './coordenador-list.component.html',
  styleUrls: ['./coordenador-list.component.css']
})
export class CoordenadorListComponent implements OnInit {
  public readonly coordenadorColumns = coordenadorColumns;
  constructor() { }

  ngOnInit(): void {
  }
}
