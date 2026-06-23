import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketResumen } from './ticket-resumen';

describe('TicketResumen', () => {
  let component: TicketResumen;
  let fixture: ComponentFixture<TicketResumen>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketResumen],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketResumen);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
