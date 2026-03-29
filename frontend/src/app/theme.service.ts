import { DOCUMENT } from '@angular/common';
import { Inject, Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export type Theme = 'light' | 'dark';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private readonly THEME_STORAGE_KEY = 'app-theme';
  private readonly DEFAULT_THEME: Theme = 'light';

  private themeSubject = new BehaviorSubject<Theme>(this.getSavedTheme());
  public theme$ = this.themeSubject.asObservable();

  constructor(@Inject(DOCUMENT) private document: Document) {
    this.initializeTheme();
  }

  private getSavedTheme(): Theme {
    if (typeof localStorage !== 'undefined') {
      try {
        const saved = localStorage.getItem(this.THEME_STORAGE_KEY);
        if (saved === 'light' || saved === 'dark') {
          return saved;
        }
      } catch {
        // localStorage might be disabled
      }
    }

    if (typeof window !== 'undefined' && window.matchMedia) {
      return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
    }

    return this.DEFAULT_THEME;
  }

  private initializeTheme(): void {
    this.applyTheme(this.themeSubject.getValue());
  }

  private applyTheme(theme: Theme): void {
    if (!this.document || typeof this.document === 'undefined') {
      return;
    }

    const body = this.document.body;
    if (!body) {
      return;
    }

    body.classList.remove('light-theme', 'dark-theme');
    body.classList.add(`${theme}-theme`);

    const root = this.document.documentElement;
    if (root) {
      root.setAttribute('data-theme', theme);
    }
  }

  public toggleTheme(): void {
    const currentTheme = this.themeSubject.getValue();
    this.setTheme(currentTheme === 'dark' ? 'light' : 'dark');
  }

  public setTheme(theme: Theme): void {
    this.themeSubject.next(theme);
    this.applyTheme(theme);

    if (typeof localStorage === 'undefined') {
      return;
    }
    try {
      localStorage.setItem(this.THEME_STORAGE_KEY, theme);
    } catch {
      // localStorage might be disabled
    }
  }

  public getTheme(): Theme {
    return this.themeSubject.getValue();
  }

  public isDarkMode(): boolean {
    return this.getTheme() === 'dark';
  }
}
