import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {professorColumns} from '../../meta-model/professor';
import {TableDialogComponent} from '../../shared/table-dialog/table-dialog.component';
import {Observable, of} from 'rxjs';
import {DataService} from '../../shared/service/data.service';
import {servidorColumns} from '../../meta-model/servidor';
import {ActivatedRoute, Router} from '@angular/router';
import {catchError, first, map} from 'rxjs/operators';
import DevExpress from "devextreme";
import notify from "devextreme/ui/notify";
import {QueryMirror} from "../../shared/query-mirror";

@Component({
  selector: 'samha-usuario-form',
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.css']
})
export class UsuarioFormComponent implements OnInit {

  form: FormGroup;
  hide: boolean = true;
  columns = professorColumns;
  resource = 'professor';
  servidor$: Observable<any>;
  papeis$: Observable<any>;
  usuario: any = {};
  constructor(private formBuilder: FormBuilder,
              public dialog: MatDialog,
              private dataService: DataService,
              private route: ActivatedRoute,
              private router: Router) {
    this.form = formBuilder.group({
      login: [null, Validators.required],
      senha: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      papel: [null, Validators.required],
      servidor_id: ['']
    })
  }

  ngOnInit(): void {
    this.route.data.pipe(first()).subscribe(
      next => {
        this.usuario = next.usuario;
        this.loadForm();
      }
    )
    this.papeis$ = this.dataService.getAll('papel');
  }


  openDialog() {
    const dialogRef = this.dialog.open(TableDialogComponent, {
      panelClass: 'custom-dialog-container',

      data: {columns: servidorColumns, resource: 'servidor', toolbarHeader: 'Servidores'},
    });

    dialogRef.afterClosed().subscribe(entityId => {
      if(entityId) {
        this.servidor$ = this.dataService.get('servidor', entityId);
      }else{
        this.servidor$ = of({
          id: null,
          nome: ''
        });
      }
    });
  }

  salvar() {
    let usuario = {
      id: this.usuario?.id,
      login: this.form.get('login').value,
      senha: this.form.get('senha').value,
      papel_id: this.form.get('papel').value,
      servidor_id: this.form.get('servidor_id').value
    }
    this.usuario = usuario;
    this.dataService.post('usuario/newUser', usuario).pipe(first()).subscribe(
      next => {
        this.router.navigate(['../', next.id], {relativeTo: this.route})
      },
      error => {
        notify(error.error.message, 'error', 2000);
      }
    );
  }

  private setUsuarioData() {
    return {
      id: null,
      login: this.form.get('login').value,
      senha: this.form.get('senha').value,
      papel_id: this.form.get('papel').value,
      servidor_id: this.form.get('servidor_id').value
    }
  }

  setProfessorValue(id) {
    this.form.get('servidor_id').setValue(id);
    this.form.get('servidor_id').disable({onlySelf: true});
    return id;
  }

  private loadForm() {
    this.form = this.formBuilder.group({
      login: [this.usuario?.login, Validators.required],
      senha: [null, [Validators.minLength(3), Validators.maxLength(20)]],
      papel: [this.usuario?.papel.id, Validators.required],
      servidor_id: []
    });

    if(this.usuario?.id) {
      this.servidor$ = this.dataService.query(new QueryMirror('servidor').selectList(['id', 'nome'])
        .where({
          and: {
            'usuario.id': {equals: this.usuario.id}
          }
        })).pipe(map(next => next.listMap[0]))
    }else {
      this.form.get('senha').addValidators([Validators.required]);
    }
    // if(this.usuario?.coordenador?.servidor?.id) this.servidor$ = this.dataService.get('servidor', this.usuario?.coordenador?.servidor?.id);

  }

  goBack() {
    this.router.navigate(['../'], {relativeTo: this.route});
  }
}
