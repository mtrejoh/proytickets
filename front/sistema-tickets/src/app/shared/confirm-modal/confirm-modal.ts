import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-confirm-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './confirm-modal.html',
})
export class ConfirmModal {
  @Input() id = 'confirmModal';
  @Input() title = 'Confirmación';
  @Input() message = '¿Desea continuar?';
  @Input() confirmText = 'Sí';
  @Input() cancelText = 'Cancelar';
  @Output() confirmed = new EventEmitter<void>();

  open(): void {
    const el = document.getElementById(this.id);
    if (el && (window as any).bootstrap) {
      const Modal = (window as any).bootstrap.Modal;
      const instance = Modal.getInstance(el) || new Modal(el);
      instance.show();
    }
  }

  close(): void {
    const el = document.getElementById(this.id);
    if (el && (window as any).bootstrap) {
      const Modal = (window as any).bootstrap.Modal;
      const instance = Modal.getInstance(el);
      if (instance) instance.hide();
    }
  }

  onConfirm(): void {
    this.confirmed.emit();
    this.close();
  }
}
