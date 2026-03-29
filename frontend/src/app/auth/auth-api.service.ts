import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthApiService {
  private apiUrl = 'http://localhost:8080/api';
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, {
      username,
      password,
    });
  }

  checkAuth(token: string): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/check-auth`,
      {},
      {
        headers: { Authorization: `Bearer ${token}` },
      },
    );
  }

  logout(token: string | null): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/logout`,
      {},
      token ? { headers: { Authorization: `Bearer ${token}` } } : undefined,
    );
  }
}
