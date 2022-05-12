import { Component, OnInit } from '@angular/core';
import {professorLogColumns} from '../../meta-model/professor';
import {servidorLogColumns} from '../../meta-model/servidor';

@Component({
  selector: 'samha-professor-log',
  templateUrl: './professor-log.component.html',
  styleUrls: ['./professor-log.component.css']
})
export class ProfessorLogComponent implements OnInit {
  professorLogColumns = professorLogColumns;
  servidorLogColumns = servidorLogColumns;

  constructor() { }

  ngOnInit(): void {
  }

}
