import { Component, Input, Output, EventEmitter } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { Router } from '@angular/router';
import { Roles } from '../../config/roles-config';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css',
})
export class SidebarComponent {
  currentUserRole = sessionStorage.getItem('userRole') ?? 'USER';
  mostrarModalSalir = false;
  
  constructor(private router: Router) {}

  @Input() collapsed = false;
  @Output() collapsedChange = new EventEmitter<boolean>();

  menu = [
    { text: 'Dashboard', route: '/app/dashboard', icon: 'bi bi-house' },
    { text: 'Usuarios', route: '/app/usuarios', icon: 'bi bi-people', role: Roles.ADMIN },
    { text: 'Personal', route: '/app/personal', icon: 'bi bi-person-badge', role: Roles.ADMIN },
    { text: 'Tickets', route: '/app/ticket', icon: 'bi bi-ticket-perforated' },
  ];

  toggleSidebar() {
    this.collapsed = !this.collapsed;
    console.log(this.collapsed);
    this.collapsedChange.emit(this.collapsed);
  }

  salirMenu(event: Event): void {
    event.preventDefault();
  }

  confirmarSalir() {
    // Limpiar el rol del usuario en la sesión.
    sessionStorage.removeItem('userRole');
    this.closeModal('modalSalir', () => this.router.navigate(['/login']));
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
