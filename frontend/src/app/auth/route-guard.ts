import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { AuthService } from "./auth.service";

@Injectable({ providedIn: 'root' })
export class SuperAdminGuard implements CanActivate {

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  canActivate(): boolean {
    // Check both the BehaviorSubject value and the token directly
    if (this.auth.isLoggedInValue() || this.auth.isLoggedIn) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
}