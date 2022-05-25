import { Component, OnInit } from '@angular/core';
import {disciplinaColumns} from '../../meta-model/disciplina';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'samha-disciplina',
  templateUrl: './disciplina.component.html',
  styleUrls: ['../../shared/table/table.component.css']
})
export class DisciplinaComponent implements OnInit {
  disciplinaColumns = disciplinaColumns;

  constructor(private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  goToLog() {
    this.router.navigate(['log'], {relativeTo: this.route});
  }
}
