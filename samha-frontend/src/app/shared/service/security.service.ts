import {HostListener, Injectable, OnDestroy} from "@angular/core";
import {AuthService} from "./auth.service";
import {first} from "rxjs/operators";
import {Router} from "@angular/router";
import {error} from "protractor";
import {NotificationService} from "./notification.service";
import {Subscription} from "rxjs";


/**
 * Caso o usuário nao mexa o mouse durante 30 minutos, ele é considerado inativo.
 * Caso ele esteja inativo no momento da renovação do access_token, sua sessão é terminada.
 * O access token está programado para vencer em 5 horas, e o refresh_token em 10 horas.
 * Se no final dessa sessão de 5 horas o usuário continuar ativo, sua sessão é renovada para 10 horas.
 * Ao atingir 9 horas e 50 minutos, o sistema enviará uma mensagem para o usuário perguntando se ele deseja manter sua sessão ativa,
 * Caso ele não responda ou cancele a mensagem, sua sessão terminará em 10 minutos.
 * Caso a resposta seja positiva, seu access_token é renovado para mais 5 horas e seu refresh token para mais 10 horas.
 * @variable isUserActive é processada pelo app.component.ts;
 */
@Injectable({providedIn: 'root'})
export class SecurityService {
  public isUserActive: boolean = false;
  private accessTokenTimer;
  private refreshTokenTimer;
  private refreshTokenWarnTimer;
  public subs: Subscription;

  constructor(private authService: AuthService,
              private router: Router,
              private notification: NotificationService) {
  }

  private getExpirationTime(token: string): number {
    const tokenParts = token.split('.');

    if (tokenParts.length !== 3) {
      // Invalid token format
      return 0;
    }

    const [, payloadBase64] = tokenParts;
    const payloadJson = atob(payloadBase64);
    const payload = JSON.parse(payloadJson);

    if (!payload.exp) {
      // Expiration time not found in the payload
      return 0;
    }

    return payload.exp * 1000; // Convert expiration time to milliseconds
  }

  private handleAccessTokenExpiration(): void {
    this.authService.refreshToken().pipe(first()).subscribe(response => {
      localStorage.setItem("access_token", JSON.stringify(response.access_token));
    })
  }

  private handleRefreshTokenExpiration() {
    let stay = confirm('Sua sessão expira em 10 minutos! Clique em OK para renovar sua sessão.');
    if (stay) {
      this.authService.refreshSession().pipe(first()).subscribe(response => {
        localStorage.clear();
        const access_token = JSON.stringify(response.access_token);
        const refresh_token = JSON.stringify(response.refresh_token);
        localStorage.setItem('access_token', access_token);
        localStorage.setItem('refresh_token', refresh_token);
        this.initialize();
      }, error => this.notification.handleError(error))
    }
  }

  public initialize() {
    const access_token = localStorage.getItem('access_token');
    const refresh_token = localStorage.getItem('refresh_token');
    this.subs = this.authService.isTokenValid().subscribe(
      valid => {
        if (access_token && refresh_token && valid) {
          this.isUserActive = true;
          const currentMillis = new Date().getTime();
          const accessTokenExpiration = this.getExpirationTime(access_token);
          const refreshTokenExpiration = this.getExpirationTime(refresh_token);

          if (currentMillis > refreshTokenExpiration) localStorage.clear();

          if (currentMillis > accessTokenExpiration && currentMillis < refreshTokenExpiration) {
            if (this.isUserActive) this.handleAccessTokenExpiration();
            else this.logout();
          }else {
            const accessTokenExpirationTime = accessTokenExpiration - currentMillis;
            const refreshTokenExpirationTime = refreshTokenExpiration - currentMillis;
            const refreshTokenWarningTime = refreshTokenExpirationTime - (590 * 60 * 1000); //a mensagem aparece 10 minutos antes de vencer o refresh_token.
            this.clearTimers();
            this.accessTokenTimer = setTimeout(() => {
              if (this.isUserActive) this.handleAccessTokenExpiration();
              else this.logout();
            }, accessTokenExpirationTime)
            this.refreshTokenWarnTimer = setTimeout(() => {
              this.handleRefreshTokenExpiration();
            }, refreshTokenWarningTime)
            this.refreshTokenTimer = setTimeout(() => {
              this.logout();
            }, refreshTokenExpirationTime)
          }
        }
      }
    )
  }

  private logout() {
    localStorage.clear();
    this.router.navigate(['login']).then(_ => window.location.reload());
  }

  private clearTimers() {
    if (this.accessTokenTimer) clearTimeout(this.accessTokenTimer);
    if (this.refreshTokenTimer) clearTimeout(this.refreshTokenTimer);
    if (this.refreshTokenWarnTimer) clearTimeout(this.refreshTokenWarnTimer);
  }
}
