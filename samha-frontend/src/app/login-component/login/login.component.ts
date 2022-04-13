import {Component, OnInit} from '@angular/core';
import {DataService} from '../../shared/data.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {createUserWithEmailAndPassword, getAuth} from '@angular/fire/auth';
import {AuthService} from '../../shared/auth.service';

@Component({
  selector: 'samha-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService) {
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
        this.authService.signIn(this.form.value.login, this.form.value.senha);
    }else{
      this.form.markAllAsTouched();
    }
  }
}
