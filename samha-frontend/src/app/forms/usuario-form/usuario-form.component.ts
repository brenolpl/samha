import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {professorColumns} from '../../meta-model/professor';
import {TableDialogComponent} from '../../shared/table-dialog/table-dialog.component';
import {Observable, of} from 'rxjs';
import {DataService} from '../../shared/service/data.service';
import {servidorColumns} from '../../meta-model/servidor';

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
  professor$: Observable<any>;
  papeis$: Observable<any>;
  constructor(private formBuilder: FormBuilder,
              public dialog: MatDialog,
              private dataService: DataService) {
    this.form = formBuilder.group({
      login: [null, Validators.required],
      senha: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      papel: [null, Validators.required]
    })
  }

  ngOnInit(): void {
    this.papeis$ = this.dataService.getAll('papel');
  }


  openDialog() {
    const dialogRef = this.dialog.open(TableDialogComponent, {
      panelClass: 'custom-dialog-container',

      data: {columns: servidorColumns, resource: 'servidor'},
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result !== null && result !== undefined) {
        this.professor$ = this.dataService.get('professor', result);
      }else{
        this.professor$ = of({
          id: -1,
          nome: ''
        });
      }
    });
  }

  salvar() {
    console.log(this.setUsuarioData());
    //this.dataService.post('resource', this.setUsuarioData())
  }

  private setUsuarioData() {
    return {
      id: null,
      login: this.form.get('login').value,
      senha: this.form.get('senha').value,
      papel: {
        id: this.form.get('papel').value
      }
    }
  }
}
