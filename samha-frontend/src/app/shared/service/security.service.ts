import {Injectable} from "@angular/core";
import {AuthService} from "./auth.service";
import {first} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class SecurityService {

  constructor(private authService: AuthService) {
  }

  private accessToken: string | null = null;
  private refreshToken: string | null = null;
  private accessTokenExpirationTimer: any; // Timer reference
  private refreshTokenExpirationTimer: any;
  private readonly expirationPercentage = 0.8;

  setTokens(accessToken: string, refreshToken: string): void {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.startExpirationTimer();
  }

  private startExpirationTimer(): void {
    if (this.accessTokenExpirationTimer) clearTimeout(this.accessTokenExpirationTimer)
    if (this.refreshTokenExpirationTimer) clearTimeout(this.refreshTokenExpirationTimer);

    const accessTokenExpirationTime = this.getExpirationTime(this.accessToken);
    const refreshTokenExpirationTime = this.getExpirationTime(this.refreshToken);
    const currentTime = new Date().getTime();
    const accessTokenRemainingTime = accessTokenExpirationTime - currentTime;
    const refreshTokenRemainingTime = refreshTokenExpirationTime - currentTime;
    const checkTime = refreshTokenRemainingTime * this.expirationPercentage;

    this.accessTokenExpirationTimer = setTimeout(() => {
      this.handleAccessTokenExpiration();
    }, accessTokenRemainingTime);
    this.refreshTokenExpirationTimer = setTimeout(() => {
      this.handleRefreshTokenExpiration();
    }, checkTime);

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
   this.authService.refreshToken().pipe(first()).subscribe( response => {
      localStorage.setItem("access_token", JSON.stringify(response.access_token));
      this.accessToken = response.access_token;
    })
  }

  private handleRefreshTokenExpiration() {
    confirm('Sessão expirando')
  }
}
