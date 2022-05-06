import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {DataService} from '../../shared/service/data.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {first, map, tap} from 'rxjs/operators';
import {Filter, QueryMirror} from '../../shared/query-mirror';
import {matrizColumns} from '../../meta-model/matriz-curricular';

@Component({
  selector: 'samha-disciplina-form',
  templateUrl: './disciplina-form.component.html',
  styleUrls: ['./disciplina-form.component.css']
})
export class DisciplinaFormComponent implements OnInit, OnDestroy {
  form: FormGroup;
  cursoControl = new FormControl(null, [Validators.required]);

  matriz$: Observable<any>;
  disciplina: any = {};
  private subscription: Subscription;

  constructor(private formBuilder: FormBuilder,
              private dataService: DataService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.route.data.pipe(first()).subscribe(
      next => {
        this.disciplina = next.disciplina;
        this.loadForm();
      }
    )

    this.subscription = this.cursoControl.valueChanges.subscribe(
      value => {
        this.setMatriz(value.id);
      }
    )

    this.form.get('matriz').valueChanges.subscribe(
      data => {
        console.log('i');
        console.log(data);
        this.form.get('matriz').addValidators(
          [Validators.max(data.curso.qtPeriodos),
            Validators.min(1)
          ])
      }
    )
  }

  private loadForm() {
    this.form = this.formBuilder.group({
      matriz: [null, Validators.required],
      nome: [null, Validators.required],
      sigla: [null],
      cargaHoraria: [60, [Validators.required, Validators.max(120), Validators.min(15)]],
      qtAulas: [null],
      periodo: [null],
      tipo: [null]
    });

  }

  private setMatriz(id: number) {
    let query = new QueryMirror('matrizCurricular');
    let projections = matrizColumns.map(column => column.columnDef);
    let filter: Filter;

    filter = {
      and: {
        'curso.id': {equals: id}
      }
    }

    query.selectList(projections);
    query.where(filter)

    this.matriz$ = this.dataService.query(query);
  }

  compareFunction(o1: string, o2: string) {
    return (o1 != null && o2 != null && o1.toUpperCase() == o2.toUpperCase());
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
