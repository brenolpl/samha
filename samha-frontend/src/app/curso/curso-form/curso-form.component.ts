import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {DataService} from '../../shared/service/data.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';
import {ActivatedRoute, Router} from '@angular/router';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'samha-curso-form',
  templateUrl: './curso-form.component.html',
  styleUrls: ['./curso-form.component.css']
})
export class CursoFormComponent implements OnInit {
  form: FormGroup;
  coordControl = new FormControl();
  curso: any = {};

  constructor(private dataService: DataService,
              private formBuilder: FormBuilder,
              private router: Router,
              private route: ActivatedRoute) {
  }


  ngOnInit() {
    this.route.data.pipe(first()).subscribe(
      next => {
        this.curso = next.curso;
        this.loadForm();
      }
    )
  }

  salvar(saveButton) {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.buildEntityData();
    if (!this.curso.id) {
      this.dataService.save('curso', this.curso).pipe(first()).subscribe(
        next => {
          this.router.navigate(['../'], {relativeTo: this.route});
        }
      );
    } else {
      this.dataService.update('curso', this.curso.id, this.curso).pipe(first()).subscribe(
        next => {
          this.router.navigate(['../'], {relativeTo: this.route});
        }
      );
    }
  }

  private buildEntityData() {
    this.curso = {
      id: this.curso?.id,
      nome: this.form.get('nome').value,
      nivel: this.form.get('nivel').value,
      qtPeriodos: this.form.get('qtPeriodo').value,
      coordenadoria: this.form.get('coord').value
    }
  }

  private loadForm() {
    this.form = this.formBuilder.group({
      nome: [this.curso?.nome, [Validators.required]],
      nivel: [this.curso?.nivel, Validators.required],
      qtPeriodo: [this.curso?.qtPeriodos, Validators.required]
    });
    this.coordControl.setValue(this.curso?.coordenadoria);
    this.form.addControl('coord', this.coordControl);
    this.form.get('coord').addValidators([Validators.required]);
  }

  compareFunction(o1: string, o2: string) {
    return (o1 != null && o2 != null && o1.toUpperCase() == o2.toUpperCase());
  }
}

