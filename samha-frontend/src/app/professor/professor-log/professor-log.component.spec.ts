import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessorLogComponent } from './professor-log.component';

describe('ProfessorLogComponent', () => {
  let component: ProfessorLogComponent;
  let fixture: ComponentFixture<ProfessorLogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfessorLogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfessorLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
