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
import { RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { GlobalStateControllService } from '../global-state-controll.service';

interface DropdownMenu {
  id: string;
  title: string;
  icon: string;
  route?: string;
  children?: { label: string; route?: string }[];
}

@Component({
  selector: 'app-side-bar',
  standalone: true,
  imports: [CommonModule, MatIconModule, RouterModule],
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css'],
})
export class SideBarComponent implements OnInit, OnDestroy {
  @ViewChild('sidebarRef') sidebarRef!: ElementRef;

  // Use global state from service
  isSidebarOpen = false;
  private sidebarSubscription!: Subscription;

  // Dropdown state (LOCAL - independent of sidebar state)
  activeDropdown: string | null = null;

  menus: DropdownMenu[] = [
    {
      id: 'dashboard',
      title: 'Dashboard',
      icon: 'dashboard',
      route: '/home',
    },
    {
      id: 'queries',
      title: 'queries',
      icon: 'queries',
      route: '/quaries',
    },
    {
      id: 'system configuration',
      title: 'system configuration',
      icon: 'system configuration',
      children: [
        { label: 'Sub-item 1', route: '/sub1' },
        { label: 'Sub-item 2', route: '/sub2' },
      ],
    },

    {
      id: 'example',
      title: 'Example Dropdown',
      icon: 'expand_more',
      children: [
        { label: 'Sub-item 1', route: '/sub1' },
        { label: 'Sub-item 2', route: '/sub2' },
      ],
    },
  ];

  constructor(
    public authService: AuthService,
    private globalStateService: GlobalStateControllService,
  ) {}

  ngOnInit(): void {
    this.sidebarSubscription = this.globalStateService.sidebarState$.subscribe(
      (state) => (this.isSidebarOpen = state),
    );
  }

  ngOnDestroy(): void {
    if (this.sidebarSubscription) {
      this.sidebarSubscription.unsubscribe();
    }
  }

  toggleDropdown(menuId: string, event: Event): void {
    event.stopPropagation();
    this.activeDropdown = this.activeDropdown === menuId ? null : menuId;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    // Close dropdown if clicked outside
    if (this.sidebarRef && this.activeDropdown) {
      const target = event.target as HTMLElement;
      if (!this.sidebarRef.nativeElement.contains(target)) {
        this.activeDropdown = null;
      }
    }
  }
}
