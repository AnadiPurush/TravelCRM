import { TestBed } from '@angular/core/testing';

import { ThemeService } from './theme.service';

describe('ThemeService', () => {
  let service: ThemeService;

  beforeEach(() => {
    spyOn(window, 'matchMedia').and.returnValue({
      matches: false,
      media: '(prefers-color-scheme: dark)',
      onchange: null,
      addListener: () => {},
      removeListener: () => {},
      addEventListener: () => {},
      removeEventListener: () => {},
      dispatchEvent: () => false,
    } as any);

    localStorage.removeItem('app-theme');
    TestBed.configureTestingModule({});
    service = TestBed.inject(ThemeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should initialize with light theme by default', () => {
    expect(service.getTheme()).toBe('light');
  });

  it('should toggle theme', () => {
    const initialTheme = service.getTheme();
    service.toggleTheme();
    const newTheme = service.getTheme();
    expect(initialTheme).not.toBe(newTheme);
  });

  it('should apply theme class to body and set data-theme', () => {
    service.setTheme('light');
    expect(document.body.classList.contains('light-theme')).toBe(true);
    expect(document.documentElement.getAttribute('data-theme')).toBe('light');
  });

  it('should persist theme in localStorage', () => {
    service.setTheme('dark');
    expect(localStorage.getItem('app-theme')).toBe('dark');
  });
});
