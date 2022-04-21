import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {LocalStorageService} from '../shared/service/local-storage.service';
import {DataService} from '../shared/service/data.service';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {TokenResponseModel} from '../meta-model/token-model';

@Component({
  selector: 'samha-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  private subscription: Subscription;

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

      this.subscription = this.dataService.login(body.toString()).subscribe(
          (result: TokenResponseModel)=> {
            this.localStorage.set("access_token", result.access_token);
            this.localStorage.set("refresh_token", result.refresh_token);
            this.route.navigate(['home']);
          },
          (error) => {
            throw error;
          }
        );
    }else{
      this.form.markAllAsTouched();
    }
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
