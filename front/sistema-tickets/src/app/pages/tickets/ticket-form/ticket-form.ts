import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TicketServiceTs } from './../../../services/ticket.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-ticket-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './ticket-form.html',
  styleUrl: './ticket-form.css',
})
export class TicketForm implements OnInit {
  ticketId = signal<string | null>(null);
  fechaActual = new Date();
  idCreador = 0;
  creador = '';
  nomcreador = '';
  ticketForm!: FormGroup;
  uploadedFile: File | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private ticketService: TicketServiceTs,
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.idCreador = sessionStorage.getItem('datUsuario')
      ? JSON.parse(sessionStorage.getItem('datUsuario')!).cvePersonal
      : 0;
    this.creador = sessionStorage.getItem('datUsuario')
      ? JSON.parse(sessionStorage.getItem('datUsuario')!).usuario
      : 'Desconocido';
    this.nomcreador = sessionStorage.getItem('datUsuario')
      ? JSON.parse(sessionStorage.getItem('datUsuario')!).nombre
      : 'Desconocido';
  }

  initForm(): void {
    this.ticketForm = this.fb.group({
      id: [null], // Oculto, solo sirve para la edición
      code: ['', [Validators.required]],
      title: ['', [Validators.required, Validators.minLength(5)]],
      description: ['', [Validators.required]],
      status: ['ABIERTO', [Validators.required]],
      prioridad: ['BAJA', [Validators.required]], // Coincide con tu JSON
      modulo: ['', [Validators.required]],
      opcionmenu: ['', [Validators.required]],
      asignado: ['', [Validators.required]],
    });
  }

  onFileChange(event: any): void {
    const file = event.target.files?.[0] ?? null;
    this.uploadedFile = file;
  }

  createTicket(): void {
    /*
    if (this.ticketForm.invalid) {

      this.ticketForm.markAllAsTouched();
      console.log("Campo invalido...." + this.ticketForm.value);
      return;
    }**/

    const formValue = this.ticketForm.value;
    const ticketPayload = {
      solicitado: this.idCreador,
      codigo: formValue.code,
      titulo: formValue.title,
      descripcion: formValue.description,
      modulo: formValue.opcionmenu,
      prioridad: formValue.prioridad,
      asignado: formValue.asignado,
      estado: formValue.status,
    };

    this.ticketService.createTicket(ticketPayload).subscribe({
      next: (response) => {
        console.log('Ticket creado', response);
        this.router.navigate(['/app/ticket']);
      },
      error: (error) => {
        console.error('Error creando ticket:', error);
      },
    });
  }

  regresarMenu() {
    this.router.navigate(['/app/ticket']);
  }
}
