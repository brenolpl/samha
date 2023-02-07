import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../../meta-model/dialog-data";
import {Observable} from "rxjs";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'samha-disciplina-dialog',
  templateUrl: './disciplina-dialog.component.html',
  styleUrls: ['./disciplina-dialog.component.css']
})
export class DisciplinaDialogComponent implements OnInit {

  entityId: number = null;
  curso$: Observable<any>;

  constructor(
    public dialogRef: MatDialogRef<DisciplinaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {}

  onNoClick(): void {
    this.entityId = null;
    this.dialogRef.close();
  }

  ngOnInit(): void {
  }

  setEntityId($event: number) {
    this.entityId = $event;
  }

  salvar(): void{
    this.dialogRef.close(this.entityId);
  }
}
