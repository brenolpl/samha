import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestricaoComponent } from './restricao.component';

describe('RestricaoComponent', () => {
  let component: RestricaoComponent;
  let fixture: ComponentFixture<RestricaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RestricaoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RestricaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
