import { Component, signal, ElementRef, ViewChild, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { getApiUrl } from '../../config/api-config';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login implements AfterViewInit {
  @ViewChild('txtTitulo') txtTitulo!: ElementRef<HTMLInputElement>;
  loginForm: FormGroup;
  submitted = signal(false);
  loginError = signal('');

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient,
  ) {
    this.loginForm = this.fb.group({
      usuario: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
/*
    if( sessionStorage !== null) {
const rol = sessionStorage.getItem('userRole');
    if( rol !== undefined) {
      this.router.navigate(['/app/dashboard']);
    }
    }
    */
    
  }

  ngAfterViewInit(): void {
    this.txtTitulo.nativeElement.focus();
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit(): void {
    this.submitted.set(true);
    this.loginError.set('');

    if (this.loginForm.invalid) {
      return;
    }

    const { usuario, password } = this.loginForm.value;

    this.http
      .post<{
        role?: string;
        rol?: string;
        token?: string;
        mensaje?: string;
        cveRol?: string;
      }>(getApiUrl('acceso'), { usuario, password })
      .subscribe({
        next: (response) => {
          console.log('Respuesta del servidor:', response);
          sessionStorage.setItem('datUsuario', JSON.stringify(response));
          sessionStorage.setItem('userRole', response.cveRol || 'USERSYS');
          /* if (response?.token) {
            sessionStorage.setItem('authToken', response.token);
          }*/
          console.log('Que paso=');
          //this.router.navigate(['/list']);
          this.router.navigate(['/app/dashboard']);
        },
        error: (err) => {
          const message =
            err?.error?.mensaje || err?.error?.message || 'Usuario o contraseña incorrectos';
          this.loginError.set(message);
        },
      });
  }
}
