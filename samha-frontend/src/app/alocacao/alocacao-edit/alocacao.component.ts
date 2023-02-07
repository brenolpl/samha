import { Component, OnInit } from '@angular/core';
import {alocacaoColumns} from "../../meta-model/alocacao";
import {ToolbarAction} from "../../meta-model/toolbar-action";

@Component({
  selector: 'samha-alocacao',
  templateUrl: './alocacao.component.html',
  styleUrls: ['./alocacao.component.css']
})
export class AlocacaoComponent implements OnInit {
  alocacaoColumns = alocacaoColumns;

  constructor() { }

  ngOnInit(): void {
  }

  onNew() {

  }
}
