import { TestBed } from '@angular/core/testing';

import { GlobalStateControllService } from './global-state-controll.service';

describe('GlobalStateControllService', () => {
  let service: GlobalStateControllService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GlobalStateControllService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
