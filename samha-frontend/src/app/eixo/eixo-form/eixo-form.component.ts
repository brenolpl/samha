import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {DataService} from '../../shared/service/data.service';
import {ActivatedRoute, Router} from '@angular/router';
import {first} from 'rxjs/operators';
import notify from "devextreme/ui/notify";
import {error} from "protractor";

@Component({
  selector: 'samha-eixo-form',
  templateUrl: './eixo-form.component.html',
  styleUrls: ['./eixo-form.component.css']
})
export class EixoFormComponent implements OnInit {
  form: FormGroup;
  eixo: any = {};

  constructor(private formBuilder: FormBuilder,
              private dataService: DataService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.route.data.pipe(first()).subscribe(
      next => {
        this.eixo = next.eixo;
        this.loadForm();
      }
    )
  }

  private loadForm() {
    this.form = this.formBuilder.group({
      nome: [this.eixo?.nome, [Validators.required, Validators.maxLength(255)]]
    })
  }

  salvar() {
    if(this.form.invalid){
      this.form.markAllAsTouched();
      return;
    }

    this.buildEntityData()

    if(this.eixo?.id){
      this.dataService.update('eixo', this.eixo.id, this.eixo).pipe(first()).subscribe(
        next => {
          notify('Eixo atualizado com sucesso!', 'success', 2000);
          this.router.navigate(['../', next.id], {relativeTo: this.route});
        },
        error => {
          notify(error?.error.message, 'error', 2000);
        }
      )
    }else{
      this.dataService.save('eixo', this.eixo).pipe(first()).subscribe(
        next => {
          notify('Eixo criado com sucesso!', 'success', 2000);
          this.router.navigate(['../', next.id], {relativeTo: this.route});
        }, error => {
          notify(error?.error.message, 'error', 2000);
        }
      )
    }
  }

  goBack = () => this.router.navigate(['../'], {relativeTo: this.route});

  private buildEntityData() {
    this.eixo = {
      id: this.eixo?.id,
      nome: this.form.get('nome').value
    }
  }

  delete() {
    this.dataService.delete('eixo', this.eixo.id).pipe(first()).subscribe(_ => {
      notify('Registro excluído com sucesso!', 'success', 2000);
      this.router.navigate(['../'], {relativeTo: this.route})
    }, error => {
      notify(error?.error?.message, 'error', 2000);
    })
  }
}
