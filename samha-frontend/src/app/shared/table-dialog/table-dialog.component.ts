import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {TableColumnModel} from '../../meta-model/table-column-model';

class DialogData {
  columns: TableColumnModel[]
  resource: string
}

@Component({
  selector: 'samha-table-dialog',
  templateUrl: './table-dialog.component.html',
  styleUrls: ['./table-dialog.component.css']
})
export class TableDialogComponent implements OnInit {

  professorId: number = null;

  constructor(
    public dialogRef: MatDialogRef<TableDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {}

  onNoClick(): void {
    this.professorId = null;
    this.dialogRef.close();
  }

  ngOnInit(): void {
  }

  setProfessor($event: number) {
    this.professorId = $event;
  }
  salvar(): void{
    this.dialogRef.close(this.professorId);
  }
}
