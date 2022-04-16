import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {LocalStorageService} from '../shared/local-storage.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'samha-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,
              private http: HttpClient,
              private localStorage: LocalStorageService) {
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
      let body = new URLSearchParams();
        body.set('login', this.form.value.login);
        body.set('senha', this.form.value.senha);

      let options = {
        headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
      };
      this.http.post('api/login', body.toString(), options).subscribe(
          (result)=> {
            console.log(result);
            //this.localStorage.set("token", result);
          },
          (error) => {
            console.log(error);
          }
        );
    }else{
      this.form.markAllAsTouched();
    }
  }

  button() {
    this.http.get("api/alocacao/1").subscribe(
      (result) =>{
        console.log(result);
      }
    )
  }
}
