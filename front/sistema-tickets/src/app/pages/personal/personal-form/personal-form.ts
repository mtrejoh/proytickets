import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { PersonalServiceTs } from '../../../services/personal.service';
import { DatPersonal } from '../../../models/datpersonal.model';

@Component({
  selector: 'app-personal-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './personal-form.html',
  styleUrl: './personal-form.css',
})
export class PersonalForm implements OnInit {
  private fb = inject(FormBuilder);
  private personalService = inject(PersonalServiceTs);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  personalState: DatPersonal | null = null;
  pathRetorno = '/app/personal';

  idPersonal = '';
  titulo = 'Nuevo Personal';

  form = this.fb.group({
    nombre: ['', [Validators.required, Validators.maxLength(100)]],
    app: ['', [Validators.required, Validators.maxLength(100)]],
    apm: ['', [Validators.required, Validators.maxLength(100)]],
    estatus: '',
  });

  ngOnInit(): void {
    const state =
      (window.history.state as {
        personal?: any[string];
        [key: string]: any;
      }) || {};

    this.personalState = state.personal ?? null;
    if (this.personalState?.cvePersonal !== undefined && this.personalState?.cvePersonal !== '') {
      this.cargarPersonal();
    } else {
      this.limpiarDatos();
    }
  }

  limpiarDatos() {
    this.idPersonal = '';
    this.titulo = 'Nuevo Personal';
    this.form.patchValue({
      nombre: '',
      app: '',
      apm: '',
      estatus: '1',
    });
  }

  cargarPersonal() {
    this.idPersonal = this.personalState?.cvePersonal || '';
    this.titulo = 'Modificar Personal';
    this.form.patchValue({
      nombre: this.personalState?.nombre,
      app: this.personalState?.app,
      apm: this.personalState?.apm,
      estatus: this.personalState?.estatus,
    });
  }

  guardar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const personal = {
      cvePersonal: this.idPersonal,
      nombre: this.form.value.nombre,
      app: this.form.value.app,
      apm: this.form.value.apm,
      estatus: this.form.value.estatus,
    };

    if (this.idPersonal == '') {
      this.personalService.guardar(personal).subscribe(() => {
        this.router.navigate([this.pathRetorno]);
      });
    } else {
      this.personalService.actualizar(personal).subscribe(() => {
        this.router.navigate([this.pathRetorno]);
      });
    }
  }

  cancelar() {
    this.router.navigate([this.pathRetorno]);
  }
}
