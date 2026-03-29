import { CommonModule } from '@angular/common';
import {
  Component,
  ElementRef,
  HostListener,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { BehaviorSubject, Subscription } from 'rxjs';
import { JwtPayload } from '../auth/auth.model';
import { AuthService } from '../auth/auth.service';
import { TokenService } from '../auth/token.service';
import { GlobalStateControllService } from '../global-state-controll.service';
import { Theme, ThemeService } from '../theme.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, ButtonModule, MatIconModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit, OnDestroy {
  // --- Global State ---
  isLoggedIn = false;
  currentTheme: Theme = 'dark';
  private authSubscription!: Subscription;
  private themeSubscription!: Subscription;
  user$ = new BehaviorSubject<JwtPayload | null>(null);

  // --- Navbar State ---
  profileDropdownOpen = false;
  @ViewChild('profileDropdownContainer', { static: false })
  profileDropdownContainer!: ElementRef<HTMLElement>;

  constructor(
    private authService: AuthService,
    private router: Router,
    private globalStateService: GlobalStateControllService,
    private tokenService: TokenService,
    private themeService: ThemeService,
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.authService.isLoggedIn$.subscribe(
      (status: boolean) => (this.isLoggedIn = status),
    );

    this.themeSubscription = this.themeService.theme$.subscribe(
      (theme: Theme) => (this.currentTheme = theme),
    );

    this.user$.next(this.tokenService.getPayLoad());
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
    if (this.themeSubscription) {
      this.themeSubscription.unsubscribe();
    }
  }

  // ==========================================
  // Global / System Logic
  // ==========================================
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    const target = event.target as HTMLElement;

    // Handle Navbar Profile Dropdown outside click
    if (this.profileDropdownOpen && this.profileDropdownContainer) {
      if (!this.profileDropdownContainer.nativeElement.contains(target)) {
        this.profileDropdownOpen = false;
      }
    }
  }

  toggleTheme(): void {
    this.themeService.toggleTheme();
  }

  toggleSidebar(): void {
    this.globalStateService.toggle();
  }

  // ==========================================
  // Navbar Logic
  // ==========================================
  toggleProfileDropdown(): void {
    this.profileDropdownOpen = !this.profileDropdownOpen;
  }

  closeProfileDropdown(): void {
    this.profileDropdownOpen = false;
  }

  navigateTo(route: string): void {
    this.closeProfileDropdown();
    this.router.navigate([route]);
  }

  logout(): void {
    this.profileDropdownOpen = false;
    this.authService.logout();
    this.router.navigate(['login']);
  }
}
