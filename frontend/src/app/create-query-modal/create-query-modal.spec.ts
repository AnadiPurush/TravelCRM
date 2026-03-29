import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateQueryModal } from './create-query-modal';

describe('CreateQueryModal', () => {
  let component: CreateQueryModal;
  let fixture: ComponentFixture<CreateQueryModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateQueryModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateQueryModal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
