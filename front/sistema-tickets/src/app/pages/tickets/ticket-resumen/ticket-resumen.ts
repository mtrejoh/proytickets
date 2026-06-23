import { Component, OnInit, signal, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { TicketServiceTs } from './../../../services/ticket.service';
import { TicketModelTs } from './../../../models/ticket.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ticket-resumen',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ticket-resumen.html',
  styleUrl: './ticket-resumen.css',
})
export class TicketResumen implements OnInit {
  ticketId = signal<string | null>(null);
  selectedTicketFromState: TicketModelTs | null = null;
  creador: string = 'Desconocido';
  seguimientoTicket = signal<any[]>([]);
  idTicket = '';
  comentario: string = '';
  cveUsuario = sessionStorage.getItem('datUsuario')
    ? JSON.parse(sessionStorage.getItem('datUsuario')!).cvePersonal
    : 'GCE-0000';
  descripcion: string = '';
  cargando = signal(false);
  estatusTicket: string = '';

  @ViewChild('htmlComentario')
  htmlComentario!: ElementRef<HTMLInputElement>;

  constructor(
    private route: ActivatedRoute,
    private ticketService: TicketServiceTs,
    private router: Router,
  ) {}

  creado: string = 'Desconocido';

  ngOnInit(): void {
    console.log('Iniciamos proceso.....');
    const routeId = this.route.snapshot.paramMap.get('id');
    this.ticketId.set(routeId);

    const state =
      (window.history.state as {
        ticket?: any[string];
        [key: string]: any;
      }) || {};

    this.creado = state.ticket?.solicitado ? state.ticket.solicitado : 'Desconocido';
    this.selectedTicketFromState = state.ticket ?? null;
    if (this.selectedTicketFromState) {
      this.selectedTicketFromState.usuario = this.creado;
      this.selectedTicketFromState.estado = state.ticket?.estatus;
    }
    this.idTicket = state.ticket?.idTicket ?? '';

    console.log(this.selectedTicketFromState);
    // Cargamos el seguimiento
    this.cargarSegTickets();
  }

  cargarSegTickets() {
    console.log('My ID: ' + this.idTicket);
    this.ticketService.getTicketSegById(this.idTicket).subscribe({
      next: (data) => {
        this.seguimientoTicket.set(data);
        console.log(data);
      },
      error: (error) => {
        console.error('Error al cargar el seguimiento del ticket:', error);
      },
    });
  }

  responder() {
    this.htmlComentario.nativeElement.focus();
  }

  publicar() {
    if (!this.comentario.trim()) {
      alert('El comentario no puede estar vacío');
      return; // No publicar si el comentario está vacío
    }

    const nuevoComentario = {
      id: this.idTicket,
      comentario: this.comentario,
      usuario: this.cveUsuario,
    };

    // Aquí podrías llamar a un servicio para guardar el comentario en el backend
    console.log('Nuevo comentario:', nuevoComentario);
    this.ticketService.createSegTicket(nuevoComentario).subscribe({
      next: (data) => {
        console.log('Comentario guardado:', data);
        // Limpiar el textarea después de publicar
        this.comentario = '';

        // Recargar el seguimiento para mostrar el nuevo comentario
        this.cargarSegTickets();
      },
      error: (error) => {
        console.error('Error al cargar el seguimiento del ticket:', error);
      },
    });
  }

  tomarTicket(ticket: any) {
    console.log('Ticket seleccionado para cancelar:', ticket);
    this.ticketService.tomarTicket(ticket.idTicket, this.cveUsuario).subscribe({
      next: (data) => {
        alert('Ticket asignado correctamente');
        if (this.selectedTicketFromState) {
          this.selectedTicketFromState.asignado = this.cveUsuario;
        }
      },
      error: (error) => {
        console.error('Error al tomar ticket:', error);
        alert('Ocurrio un error al tomar el ticket');
      },
    });
  }

  seleccionarTicketCancelar(ticket: any) {
    this.descripcion = '';
    this.removeModalBackdrop();
    console.log('Ticket seleccionado para cancelar:', ticket);

    const modalEl = document.getElementById('modalCancelar');

    if (modalEl) {
      const bootstrap = (window as any).bootstrap;
      const modal = bootstrap.Modal.getOrCreateInstance(modalEl);
      modal.show();
    }
  }

  onSelectChange(event: Event): void {
    this.estatusTicket = (event.target as HTMLSelectElement).value;
  }

  confirmarCancelar(ticket: any) {
    if( this.estatusTicket === '') {
      alert('Por favor, selecciona el motivo del cierre del ticket.');
      return;
    }
    if (!this.descripcion || this.descripcion.trim() === '') {
      alert('Por favor, ingresa una descripción para el cierre del ticket.');
      return;
    }

    this.cargando.set(true);
    this.ticketService
      .closeTicket(ticket.idTicket, this.descripcion, this.cveUsuario, this.estatusTicket)
      .subscribe({
        next: (data) => {
          this.cargando.set(false);

          this.closeModal('modalCancelar');
          this.retornarMenu();
        },
        error: (error) => {
          console.error('Error al cancelar ticket:', error);
          this.cargando.set(false);

          this.closeModal('modalCancelar');
        },
      });
  }

  retornarMenu() {
    this.router.navigate(['/app/ticket']);
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

  private removeModalBackdrop(): void {
    document.body.classList.remove('modal-open');
    document.body.style.removeProperty('overflow');
    document.body.style.removeProperty('padding-right');
    // --------------------
    const backdrops = Array.from(document.querySelectorAll('.modal-backdrop'));
    backdrops.forEach((backdrop) => backdrop.remove());
  }
}
