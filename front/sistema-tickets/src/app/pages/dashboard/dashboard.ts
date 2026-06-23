import { Component, signal } from '@angular/core';
import { TicketServiceTs } from '../../services/ticket.service';
import { Router } from '@angular/router';
import { Roles } from '../../config/roles-config';

@Component({
  selector: 'app-dashboard',
  imports: [],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  cargando = signal(false);
  dataDashboard = signal<any[]>([]);
  currentUserRole = sessionStorage.getItem('userRole') ?? 'USER';
  roles = Roles;
  
  constructor(
    private ticketServices: TicketServiceTs,
    private router: Router,
  ) {
    this.cargar();
  }

  cargar() {
    this.cargando.set(true);

    this.ticketServices.getDashboard().subscribe({
      next: (data) => {
        this.dataDashboard.set(
          Object.entries(data).map(([key, value]) => ({
            estatus: key,
            total: value,
          })),
        );
        this.cargando.set(false);
      },
      error: () => {
        this.cargando.set(false);
      },
    });
  }

  nuevoTicket() {
    this.router.navigate(['/app/ticket/nuevo']);
  }

  verTickets() {
    this.router.navigate(['/app/ticket']);
  }

  verUsuarios() {
    this.router.navigate(['/app/usuarios']);
  }
}
