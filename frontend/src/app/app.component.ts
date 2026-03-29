import { CommonModule } from '@angular/common';
import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Subscription } from 'rxjs/internal/Subscription';
import { AuthService } from './auth/auth.service';
import { GlobalStateControllService } from './global-state-controll.service';
import { NavbarComponent } from './navbar/navbar.component';
import { SideBarComponent } from './side-bar/side-bar.component';
import { ThemeService } from './theme.service';
@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [CommonModule, SideBarComponent, NavbarComponent, RouterOutlet],
})
export class AppComponent implements OnInit, OnDestroy {
  isSidebarOpen = false;
  isLoggedIn = false;
  private sidebarSubscription!: Subscription;
  private authSubscription!: Subscription;

  constructor(
    private authService: AuthService,
    private state: GlobalStateControllService,
    private themeService: ThemeService,
  ) {}

  ngOnInit() {
    this.authService.initialize();

    this.authSubscription = this.authService.isLoggedIn$.subscribe(
      (status: boolean) => (this.isLoggedIn = status),
    );

    this.sidebarSubscription = this.state.sidebarState$.subscribe(
      (state) => (this.isSidebarOpen = state),
    );
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
    this.sidebarSubscription?.unsubscribe();
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    if (this.isSidebarOpen) {
      const target = event.target as HTMLElement;
      const sidebar = document.querySelector('.sidebar');
      if (sidebar && !sidebar.contains(target)) {
        this.state.close();
      }
    }
  }
}
