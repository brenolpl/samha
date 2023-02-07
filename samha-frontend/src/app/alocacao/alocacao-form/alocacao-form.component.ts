import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Observable, of} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {DataService} from "../../shared/service/data.service";
import {ActivatedRoute} from "@angular/router";
import {TableDialogComponent} from "../../shared/table-dialog/table-dialog.component";
import {servidorColumns} from "../../meta-model/servidor";
import {ComponentType} from "@angular/cdk/overlay";
import {DisciplinaComponent} from "../../disciplina/disciplina/disciplina.component";
import {disciplinaColumns} from "../../meta-model/disciplina";
import {DialogData} from "../../meta-model/dialog-data";
import {DisciplinaListComponent} from "../../disciplina/disciplina-list/disciplina-list.component";
import {DisciplinaDialogComponent} from "../../disciplina/disciplina-dialog/disciplina-dialog.component";

@Component({
  selector: 'samha-alocacao-new',
  templateUrl: './alocacao-form.component.html',
  styleUrls: ['./alocacao-form.component.css']
})
export class AlocacaoFormComponent implements OnInit {
  form: FormGroup;
  disciplina$: Observable<any>;
  disciplinaData = {columns: disciplinaColumns, resource: 'disciplina', toolbarHeader: 'Disciplinas'};
  obs$: Observable<any>;

  constructor(private formBuilder: FormBuilder,
              public dialog: MatDialog,
              private dataService: DataService,
              private route: ActivatedRoute) {
    this.form = this.formBuilder.group({
      disciplina_id: [null]
    })
  }

  ngOnInit(): void {
  }

  setDisciplinaValue(id) {

  }

  salvar() {

  }

  getDisciplinaComponent(): ComponentType<any> {
    return DisciplinaDialogComponent
  }

  openDialog(component: ComponentType<any>, data: DialogData) {
    const dialogRef = this.dialog.open(component, {
      panelClass: 'custom-dialog-container',
      data: data,
    });

    dialogRef.afterClosed().subscribe(entityId => {
      if(entityId) {
        this.obs$ = this.dataService.get(data.resource, entityId);
      }else{
        this.obs$ = of({
          id: -1,
          nome: ''
        });
      }
    });
  }
}
