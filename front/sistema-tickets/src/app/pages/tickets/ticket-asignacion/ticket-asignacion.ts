import { ServicioServiceTs } from './../../../services/servicio.service';
import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TicketModelTs } from './../../../models/ticket.model';

@Component({
  selector: 'app-ticket-asignacion',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './ticket-asignacion.html',
  styleUrls: ['./ticket-asignacion.css'],
})
export class TicketAsignacion implements OnInit {
  ticketForm!: FormGroup;
  lstUsuarios = signal<any[]>([]);
  ticketState: TicketModelTs | null = null;
  idTicket: string = '';
  cargando = signal(false);
  colaborador: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private servicioService: ServicioServiceTs,
    private router: Router,
  ) {}

  ngOnInit(): void {
    const state =
      (window.history.state as {
        ticket?: any[string];
        [key: string]: any;
      }) || {};

    this.initForm();
    this.idTicket = this.route.snapshot.paramMap.get('id') || '';
    this.ticketState = state.ticket ?? null;
    this.colaborador = this.ticketState?.asignado || null;

    // Cargamos los usuarios
    this.cargarLstUsuarios();
  }

  initForm(): void {
    this.ticketForm = this.fb.group({
      ticketId: [null, [Validators.required]],
      asignado: ['', [Validators.required]],
    });
  }

  cargarLstUsuarios() {
    this.cargando.set(true);
    this.servicioService.getUserService().subscribe({
      next: (data) => {
        this.lstUsuarios.set(data);
        this.cargando.set(false);
      },
      error: (error) => {
        console.error('Error al cargar tickets:', error);
        this.cargando.set(false);
      },
    });
  }

  onSubmit(): void {
    if (this.ticketForm.invalid) {
      this.ticketForm.markAllAsTouched();
      // return;
    }
    const asignado = this.ticketForm.value.asignado;
    const colaboradorSeleccionado = this.lstUsuarios().find(
      (usuario) => usuario.idUsuario === asignado,
    );
    console.log( colaboradorSeleccionado.usuario === this.colaborador );
    if(colaboradorSeleccionado.usuario === this.colaborador) {
      alert('El usuario asignado debe ser diferente');
      return;
    }

    console.log( asignado+' - '+this.idTicket);
    
    this.servicioService.sendAtener(asignado, this.idTicket ).subscribe({
      next: (data) => {
        this.regresarMenu();
      },
      error: (error) => {
        console.error('Error al cargar tickets:', error);
        this.cargando.set(false);
      },
    });


    /*
    this.ticketService.updateTicket(ticketId, ticketActualizado).subscribe({
      next: () => {
        this.mensaje = `Ticket ${ticket.codigo} asignado a ${asignado}.`;
      },
      error: () => {
        this.mensaje = `No se pudo asignar el ticket ${ticket.codigo}.`; 
      },
    });*/
  }

  regresarMenu(): void {
    this.router.navigate(['/app/ticket']);
  }
}
