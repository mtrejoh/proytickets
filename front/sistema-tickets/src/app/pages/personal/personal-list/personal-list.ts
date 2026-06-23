import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { PersonalServiceTs } from '../../../services/personal.service';
import { DatPersonal } from '../../../models/datpersonal.model';

@Component({
  selector: 'app-personal-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './personal-list.html',
})
export class PersonalList implements OnInit {
  personal = signal<DatPersonal[]>([]);
  cargando = signal(false);
  mostrarConfirmacion = signal(false);
  personaSeleccionada: any;

  constructor(
    private personalService: PersonalServiceTs,
    private router: Router,
  ) {}

  ngOnInit() {
    this.cargar();
  }

  cargar() {
    this.cargando.set(true);

    this.personalService.listarPersonal().subscribe({
      next: (data) => {
        this.personal.set(data);
        this.cargando.set(false);
      },

      error: () => {
        this.cargando.set(false);
      },
    });
  }

  nuevo() {
    this.router.navigate(['/app/personal/nuevo']);
  }

  editar(personal: DatPersonal) {
    console.log(personal.cvePersonal);
    this.router.navigate(['/app/personal/editarpersonal', personal.cvePersonal], {
      state: { personal },
    });
  }

  eliminar(personal: DatPersonal) {
    this.personaSeleccionada = personal;
    // Aquí abrirías tu ConfirmModal
    this.mostrarConfirmacion.set(true);
  }
  cerrarModalConfirmacion() {
    this.mostrarConfirmacion.set(false);
  }

  cambiaEstatus() {
    this.cargando.set(false);
    this.personalService.actualizarStatus(this.personaSeleccionada).subscribe({
      next: (data) => {
        this.cargar();
      },
      error: () => {
      },
    });
    this.mostrarConfirmacion.set(false);
  }
}
