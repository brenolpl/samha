import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {LocalStorageService} from '../shared/local-storage.service';
import {TokenResponseModel} from '../shared/common-model';
import {DataService} from '../shared/data.service';
import {Router} from '@angular/router';

@Component({
  selector: 'samha-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,
              private localStorage: LocalStorageService,
              private dataService: DataService,
              private route: Router) {
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

      this.dataService.post('login', body.toString()).subscribe(
          (result: TokenResponseModel)=> {
            this.localStorage.set("access_token", result.access_token);
            this.localStorage.set("refresh_token", result.refresh_token);
            //HACK: metodo navigate estava redirecionando antes dos tokens serem setados no localstorage, fazendo com que a tela
            //home não carregasse
            new Promise( f => setTimeout(f, 1000)).then(result => this.route.navigate(['home']));
          },
          (error) => {
            throw error;
          }
        );
    }else{
      this.form.markAllAsTouched();
    }
  }
}
