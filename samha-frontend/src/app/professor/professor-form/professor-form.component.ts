import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Observable} from 'rxjs';
import {DataService} from '../../shared/service/data.service';

@Component({
  selector: 'samha-professor-form',
  templateUrl: './professor-form.component.html',
  styleUrls: ['./professor-form.component.css']
})
export class ProfessorFormComponent implements OnInit {
  form: FormGroup;
  coordenadoria$: Observable<any>;

  constructor(private dataService: DataService,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      nome: [],
      email: [],
      matricula: [],
      carga_horaria: [],
      coordenadoria: []
    });
    this.coordenadoria$ = this.dataService.getAll('coordenadoria');
  }

  onSideBarClicked() {

  }
}
