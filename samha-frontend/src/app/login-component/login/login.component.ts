import {Component, OnInit} from '@angular/core';
import {DataService} from '../../shared/data.service';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'samha-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private dataService: DataService,
              private formBuilder: FormBuilder) {
  }

  form: FormGroup;

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      login: [null],
      senha: [null]
    });
  }

  login(): void {
    if (this.form.valid){
      this.dataService.post('login', this.form.value).subscribe(
          data => {
              console.log(data);
          }, error => {
            console.log(error);
        }
      );
    }else{
      this.form.markAllAsTouched();
    }
  }
}
