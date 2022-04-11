import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'samha-samha-form',
  templateUrl: './samha-form.component.html',
  styleUrls: ['./samha-form.component.css']
})
export class SamhaFormComponent implements OnInit {

  form: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
      /*this.form = new FormGroup({
          login: new FormControl(null),
          senha: new FormControl(null)
      });*/
      this.form = this.formBuilder.group({
        login: [null],
        senha: [null]
      });
  }

}
