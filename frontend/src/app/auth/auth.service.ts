// src/app/auth.service.ts
import { Injectable } from '@angular/core';

import { BehaviorSubject, catchError, tap, throwError } from 'rxjs';

import { AuthApiService } from './auth-api.service';
import { JwtPayload } from './auth.model';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  [x: string]: any;
  private _isLoggedIn = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this._isLoggedIn.asObservable();

  constructor(
    private authApi: AuthApiService,
    private tokenService: TokenService,
  ) {
    // restore state from localStorage after refresh
    this._isLoggedIn.next(!!this.tokenService.getToken());
  }

  initialize(): void {
    const token = this.tokenService.getToken();
    if (token) {
      this._isLoggedIn.next(true);
      this.checkLoginInStatus();
    } else {
      this._isLoggedIn.next(false);
    }
  }

  private get payload(): JwtPayload | null {
    return this.tokenService.getPayLoad();
  }

  checkLoginInStatus(): void {
    const token = this.tokenService.getToken();
    if (this.tokenService.isTokenExpired()) {
      this['logout']();
      return;
    }
    if (!token) {
      this._isLoggedIn.next(false);
      return;
    }
    this.authApi
      .checkAuth(token)
      .pipe(
        tap(() => {
          this._isLoggedIn.next(true);
        }),
        catchError((err) => {
          console.error('AuthService: Token verification failed', err);
          // clear stale token and state so refresh keeps auth in sync
          this.logout();
          return throwError(() => err);
        }),
      )
      .subscribe();
  }

  login(username: string, password: string) {
    return this.authApi.login(username, password).pipe(
      tap((res) => {
        this.tokenService.setToken(res.token);
        this._isLoggedIn.next(true);
      }),
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    sessionStorage.removeItem('token');
    this.tokenService.clearToken();
    this._isLoggedIn.next(false);
  }
  isSuperAdmin(): boolean {
    const result = this.payload?.superAdmin === true;
    return result;
  }
  hasPermission(perm: string): boolean {
    const perms = this.payload?.permissions ?? [];
    const result = perms.includes(perm);

    return result;
  }
  get isLoggedIn(): boolean {
    return !!this.tokenService.getToken();
  }
  isLoggedInValue(): boolean {
    return this._isLoggedIn.value;
  }
}
