import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestricaoListComponent } from './restricao-list.component';

describe('RestricaoListComponent', () => {
  let component: RestricaoListComponent;
  let fixture: ComponentFixture<RestricaoListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RestricaoListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RestricaoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
