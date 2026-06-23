import { Component, computed, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TicketServiceTs } from '../../../services/ticket.service';
import { FormsModule } from '@angular/forms';
import { Roles } from '../../../config/roles-config';


@Component({
  selector: 'app-ticket-list-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ticket-list.html',
})
export class TicketList implements OnInit {
  ticketSeleccionado: any = null;
  cargando = signal(false);
  textoBusqueda = signal('');
  paginaActual = signal(1);
  registrosPorPagina = signal(20);
  tickets = signal<any[]>([]);
  roles = Roles;
  currentUserRole = sessionStorage.getItem('userRole') ?? 'USER';
  nomUsuario = sessionStorage.getItem('datUsuario')
    ? JSON.parse(sessionStorage.getItem('datUsuario')!).nombre
    : 'Desconocido';
  cveUsuario = sessionStorage.getItem('datUsuario')
    ? JSON.parse(sessionStorage.getItem('datUsuario')!).cvePersonal
    : 'GCE-0000';
  descripcion: string = '';


  constructor(
    private ticketService: TicketServiceTs,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.cargarTickets();
  }

  cargarTickets() {
    this.cargando.set(true);
    this.ticketService.getTickets(this.paginaActual(), this.registrosPorPagina()).subscribe({
      next: (data) => {
        this.tickets.set(data);
        this.cargando.set(false);
      },
      error: (error) => {
        console.error('Error al cargar tickets:', error);
        this.cargando.set(false);
      },
    });
  }

  get puedeAsignarTicket(): boolean {
    console.log('Rol actual:', this.currentUserRole);
    return this.currentUserRole === Roles.ADMIN;
  }

  irANuevoTicket() {
    this.router.navigate(['/app/ticket/nuevo']);
  }

  irAUsuarios() {
    console.log('Navegando a la gestión de usuarios');
    this.router.navigate(['/app/usuarios']);
  }


  asignarTicket(ticket: any) {
    console.log('Asignando ticket:', ticket);
    this.router.navigate(['/app/ticket/asignar', ticket.idTicket], { state: { ticket } });
  }

  ticketsFiltrados = computed(() => {
    const texto = this.textoBusqueda().toLowerCase().trim();
    if (!texto) {
      return this.tickets();
    }
    return this.tickets().filter(
      (ticket) =>
        ticket.nomSolicitado.toLowerCase().includes(texto) ||
        ticket.titulo.toLowerCase().includes(texto) ||
        ticket.descripcion.toLowerCase().includes(texto),
    );
  });

  ticketsPaginados = computed(() => {
    const inicio = (this.paginaActual() - 1) * this.registrosPorPagina();
    const fin = inicio + this.registrosPorPagina();
    return this.ticketsFiltrados().slice(inicio, fin);
  });

  totalPaginas = computed(() =>
    Math.ceil(this.ticketsFiltrados().length / this.registrosPorPagina()),
  );

  cambiarPagina(pagina: number) {
    if (pagina >= 1 && pagina <= this.totalPaginas()) {
      this.paginaActual.set(pagina);
    }
  }

  getEstiloPrioridad(prioridad: string) {
    switch (prioridad) {
      case 'ALTA':
        return 'bg-danger';
      case 'MEDIA':
        return 'bg-warning text-dark';

      default:
        return 'bg-info text-dark';
    }
  }

  seleccionarTicket(ticket: any) {
    console.log('Ticket seleccionado:', ticket);
    this.router.navigate(['/app/ticket/resumen', ticket.idTicket], { state: { ticket } });
  }

  seleccionarTicketCancelar(ticket: any) {
    this.descripcion = '';
     this.removeModalBackdrop();
    console.log('Ticket seleccionado para cancelar:', ticket);
    this.ticketSeleccionado = ticket;

    const modalEl = document.getElementById('modalCancelar');

    if (modalEl) {
      const bootstrap = (window as any).bootstrap;
      const modal = bootstrap.Modal.getOrCreateInstance(modalEl);
      modal.show();
    }
  }

  confirmarCancelar(ticket: any) {
    if (!this.descripcion || this.descripcion.trim() === '') {
      alert('Por favor, ingresa una descripción para la cancelación.');
      return;
    }
    
    this.cargando.set(true);
    this.ticketService.deleteTicket(ticket.idTicket, this.descripcion, this.cveUsuario).subscribe({
      next: (data) => {
        this.cargando.set(false);
        // Eliminar el ticket de la lista
        this.tickets.set(
          this.tickets().map((t) =>
            t.idTicket === this.ticketSeleccionado.idTicket ? { ...t, estatus: 'CANCELADO' } : t,
          ),
        );

        this.closeModal('modalCancelar');
      },
      error: (error) => {
        console.error('Error al cancelar ticket:', error);
        this.cargando.set(false);

        this.closeModal('modalCancelar');
      },
    });
    
  }

  private removeModalBackdrop(): void {
    document.body.classList.remove('modal-open');
    document.body.style.removeProperty('overflow');
    document.body.style.removeProperty('padding-right');
    // --------------------
    const backdrops = Array.from(document.querySelectorAll('.modal-backdrop'));
    backdrops.forEach((backdrop) => backdrop.remove());
  }

  private closeModal(modalId: string, callback?: () => void): void {
    const modalEl = document.getElementById(modalId);
    if (!modalEl) {
      callback?.();
      return;
    }

    const bootstrap = (window as any).bootstrap;
    const hiddenHandler = () => {
      this.removeModalBackdrop();
      callback?.();
      modalEl.removeEventListener('hidden.bs.modal', hiddenHandler);
    };

    modalEl.addEventListener('hidden.bs.modal', hiddenHandler, { once: true });

    if (bootstrap?.Modal) {
      const instance = bootstrap.Modal.getInstance(modalEl) || new bootstrap.Modal(modalEl);
      instance.hide();
    } else {
      this.removeModalBackdrop();
      callback?.();
    }
  }


}
