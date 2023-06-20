import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {AuthService} from "../../shared/service/auth.service";
import {error} from "protractor";
import {logger} from "codelyzer/util/logger";
import {of, Subscription} from "rxjs";
import {catchError} from "rxjs/operators";

@Component({
  selector: 'samha-relatorio',
  templateUrl: './relatorio.component.html',
  styleUrls: ['./relatorio.component.css']
})
export class RelatorioComponent implements OnInit, OnDestroy {
  public semestreControl: FormControl;
  public anoControl: FormControl;
  private loggedSub: Subscription
  public tabs = [
    {
      text: 'Turmas',
      id: 0
    },
    {
      text: 'Professores',
      id: 1
    },
    {
      text: 'Disciplinas',
      id: 2
    }
  ];
  public selectedTab: number = 0;

  constructor(private authService: AuthService) {
    this.anoControl = new FormControl(new Date().getFullYear());
    this.semestreControl = new FormControl(new Date().getMonth() < 6 ? 1 : 2);
  }

  ngOnInit(): void {
    this.loggedSub = this.authService.isTokenValid().subscribe(
      () => {},
      error => {
        this.anoControl.addValidators([Validators.max(new Date().getFullYear())])
        this.semestreControl.addValidators([Validators.max(new Date().getMonth() < 6 ? 1 : 2)]);
        return of(false);
      }
    )
  }

  selectTab = (e: any) => this.selectedTab = e.itemIndex;

  ngOnDestroy(): void {
    this.loggedSub?.unsubscribe();
  }
}
