import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { JwtPayload } from './auth.model';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  private payload = new BehaviorSubject<JwtPayload | null>(null);
  payload$ = this.payload.asObservable();

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    this.initFromStorage();
  }

  private isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }

  setToken(token: string): void {
    if (!this.isBrowser()) return;

    localStorage.setItem('token', token);

    const decoded = this.decodeToken(token);
    this.payload.next(decoded);
  }

  getToken(): string | null {
    if (!this.isBrowser()) return null;
    return localStorage.getItem('token');
  }

  clearToken(): void {
    if (!this.isBrowser()) return;
    sessionStorage.removeItem('token');

    localStorage.removeItem('token');
    this.payload.next(null);
  }

  decodeToken(token: string): JwtPayload | null {
    try {
      const payload = token.split('.')[1];
      const decoded = atob(payload);
      return JSON.parse(decoded);
    } catch {
      return null;
    }
  }

  initFromStorage(): void {
    const token = this.getToken();

    if (token) {
      const decoded = this.decodeToken(token);
      this.payload.next(decoded);
    }
  }

  getPayLoad(): JwtPayload | null {
    return this.payload.getValue();
  }
  isTokenExpired(): boolean {
    const payload = this.getPayLoad();

    if (!payload || !payload.exp) return true;

    const currentTime = Math.floor(Date.now() / 1000);

    return payload.exp < currentTime;
  }
}
