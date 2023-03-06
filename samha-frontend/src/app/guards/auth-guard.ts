import {EventEmitter, Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable, of, Subscription} from 'rxjs';
import {LocalStorageService} from '../shared/service/local-storage.service';
import {catchError, map, tap} from 'rxjs/operators';
import {AuthService} from '../shared/service/auth.service';

@Injectable()
export class AuthGuard implements CanActivate{

  response: EventEmitter<void> = new EventEmitter<void>();

  constructor(private localStorage: LocalStorageService,
              private router: Router,
              private authService: AuthService) {
  }


  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | boolean{
    return this.authService.isTokenValid().pipe(
      map(
        data => {
          this.response.emit();
          return true;
        }
      ),
      catchError( _ => {
        this.response.emit();
        this.router.navigate(['/login']).then(_ => location.reload());
        return of(false);
      })
    )
  }
}
