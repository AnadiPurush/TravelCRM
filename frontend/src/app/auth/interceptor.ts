import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { TokenService } from './token.service';

const API_BASE = 'http://localhost:8080';

export const Interceptor: HttpInterceptorFn = (req, next) => {
  const tokenService = inject(TokenService);
  const token = tokenService.getToken();

  let modifiedReq = req;

  // Attach base URL only if request is relative
  if (!req.url.startsWith('http')) {
    modifiedReq = req.clone({
      url: `${API_BASE}/${req.url}`,
    });
  }

  // Attach Authorization header if token exists
  if (token) {
    modifiedReq = modifiedReq.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  return next(modifiedReq);
};
