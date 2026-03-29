import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuariesComponent } from './quaries.component';

describe('QuariesComponent', () => {
  let component: QuariesComponent;
  let fixture: ComponentFixture<QuariesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QuariesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuariesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
