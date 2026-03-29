import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GlobalStateControllService {
  private sidebarState = new BehaviorSubject<boolean>(false);

  sidebarState$ = this.sidebarState.asObservable();
  constructor() {}

  toggle() {
    this.sidebarState.next(!this.sidebarState.value);
  }

  open() {
    this.sidebarState.next(true);
  }

  close() {
    this.sidebarState.next(false);
  }
}
