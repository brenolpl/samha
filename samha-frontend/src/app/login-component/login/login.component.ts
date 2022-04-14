import {Component, OnInit} from '@angular/core';
import {DataService} from '../../shared/data.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {createUserWithEmailAndPassword, getAuth} from '@angular/fire/auth';
import {AuthService} from '../../shared/auth.service';
import {LocalStorageService} from '../../shared/local-storage.service';

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
      email: [null],
      senha: [null]
    });
  }

  login(): void {
    if (this.form.valid){
        this.authService.signIn(this.form.value.email, this.form.value.senha);
    }else{
      this.form.markAllAsTouched();
    }
  }
}
