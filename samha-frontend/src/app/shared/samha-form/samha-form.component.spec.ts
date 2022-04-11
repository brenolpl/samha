import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SamhaFormComponent } from './samha-form.component';

describe('SamhaFormComponent', () => {
  let component: SamhaFormComponent;
  let fixture: ComponentFixture<SamhaFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SamhaFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SamhaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
