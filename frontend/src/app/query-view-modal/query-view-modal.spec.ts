import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QueryViewModal } from './query-view-modal';

describe('QueryViewModal', () => {
  let component: QueryViewModal;
  let fixture: ComponentFixture<QueryViewModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QueryViewModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QueryViewModal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
