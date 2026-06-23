import { ServicioServiceTs } from './../../../services/servicio.service';
import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
  FormsModule,
} from '@angular/forms';
import { Router } from '@angular/router';
import { PersonalServiceTs } from '../../../services/personal.service';
import { PersonalModelTs } from '../../../models/personal.model';

@Component({
  selector: 'app-users-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './users.html',
})
export class UsersPage implements OnInit {
  userForm: FormGroup;
  formSubmitted = signal(false);
  formMessage = signal('');
  cargando = signal(false);
  lstPersonas = signal<any[]>([]);
  lstRoles = signal<any[]>([]);
  mostrarRoles = signal(false);
  mostrarModal = signal(false);
  mostrarConfirmacion = signal(false);

  personaSeleccionada: any;
  nuevoUsuario = {
    cvePersonal: '',
    usuario: '',
    password: '',
    idRol: '',
  };

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private personalService: PersonalServiceTs,
    private serviceSevice: ServicioServiceTs,
  ) {
    this.userForm = this.fb.group({
      nombre: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      role: ['INVITADO', Validators.required],
    });
  }

  ngOnInit(): void {
    this.obtenerPersonal();
    this.obtenerRoles();
  }

  obtenerPersonal() {
    this.cargando.set(true);
    this.personalService.getPersonalActivo().subscribe({
      next: (data) => {
        this.lstPersonas.set(data);
        this.cargando.set(false);
      },
      error: (error) => {
        console.error('Error al cargar tickets:', error);
        this.cargando.set(false);
      },
    });
  }


  /**
   * Obetenemos los roles de los usuarios
   */
  obtenerRoles() {
    this.serviceSevice.getRol().subscribe({
      next: (data) => {
        this.lstRoles.set(data);
      },
      error: (error) => {
        console.error('Error al cargar los roles:', error);
      },
    });
  }


  cambiaRol(persona: PersonalModelTs) {
    this.personaSeleccionada = persona;
    this.mostrarRoles.set(true);
  }

  crearUsuario(persona: PersonalModelTs): void {
    this.personaSeleccionada = persona;
    this.nuevoUsuario = {
      cvePersonal: '',
      usuario: '',
      password: '',
      idRol: '',
    };
    this.mostrarModal.set(true);
  }


  /**
   * Metodo para guardar un nuevo usuario
   * @returns 
   */
  guardarUsuario(): void {
    if (
      this.nuevoUsuario.usuario === '' ||
      this.nuevoUsuario.password === '' ||
      this.nuevoUsuario.idRol === ''
    ) {
      alert('Faltan datos importantes..');
      return;
    }
    const payload = {
      cvePersonal: this.personaSeleccionada.cvePersonal,
      usuario: this.nuevoUsuario.usuario,
      password: this.nuevoUsuario.password,
      cveRol: this.nuevoUsuario.idRol,
    };

    this.personalService.createUsuario(payload).subscribe({
      next: (data) => {
        this.obtenerPersonal();
        this.cerrarModal();
      },
      error: (err) => console.error(err),
    });
  }


  /**
   * Metodo para cambiar el pass o status del usuario
   *
   * @param persona
   */
  abrirModalCambiaPass(persona: PersonalModelTs) {
    this.personaSeleccionada = persona;
     this.nuevoUsuario = {
      cvePersonal: '',
      usuario: '',
      password: '',
      idRol: '',
    };
    
    this.mostrarConfirmacion.set(true);
  }

  /**
   * Activamos para el cambio de Password
   */
  cambiaPass() {   
    const payload = {
      cvePersonal: this.personaSeleccionada.cvePersonal,
      usuario: this.personaSeleccionada.cveUsuario ,
    };     
    this.personalService.cambiarStatus(payload).subscribe({
      next: (data) => {
        this.obtenerPersonal();
        this.cerrarModalConfirmacion();
      },
      error: (err) => console.error(err),
    });
  }


  cambiarRol() {
    if (this.nuevoUsuario.idRol === '') {
      alert('Elija el nuevo rol..');
      return;
    }
    const payload = {
      cvePersonal: this.personaSeleccionada.cvePersonal,
      usuario: this.personaSeleccionada.cveUsuario,
      cveRol: this.nuevoUsuario.idRol,
    };
    this.personalService.cambiarRol(payload).subscribe({
      next: (data) => {
        this.obtenerPersonal();
        this.cerrarRoles();
      },
      error: (err) => console.error(err),
    });
  }

  /**
   * Cerrar modulo de confirmacion
   */
  cerrarModalConfirmacion() {
    this.mostrarConfirmacion.set(false);
  }

  /**
   * Cerrar modal
   */
  cerrarModal(): void {
    this.mostrarModal.set(false);
  }

  /**
   * Cerramos modal del Cambio de Roles
   */
  cerrarRoles() : void {
    this.mostrarRoles.set(false);
  }
}
