import { Routes } from '@angular/router';
import { TicketList } from './pages/tickets/ticket-list/ticket-list';
import { TicketForm } from './pages/tickets/ticket-form/ticket-form';
import { TicketAsignacion } from './pages/tickets/ticket-asignacion/ticket-asignacion';
import { TicketResumen } from './pages/tickets/ticket-resumen/ticket-resumen';
import { Login } from './auth/login/login';
import { UsersPage } from './pages/usuarios/users/users';
import { AuthGuard, AdminGuard } from './auth.guard';
import { PersonalList } from './pages/personal/personal-list/personal-list';
import { PersonalForm } from './pages/personal/personal-form/personal-form';
import { LayoutComponent } from './layout/layout/layout.component';
import { Dashboard } from './pages/dashboard/dashboard';

export const routes: Routes = [
  {
    path: 'app',
    component: LayoutComponent,
    children: [
      { path: 'dashboard', component: Dashboard },
      { path: 'ticket', component: TicketList, canActivate: [AuthGuard] },
      { path: 'ticket/nuevo', component: TicketForm, canActivate: [AuthGuard] },
      { path: 'ticket/asignar/:id', component: TicketAsignacion, canActivate: [AuthGuard] },
      { path: 'ticket/editar/:id', component: TicketForm, canActivate: [AuthGuard] },
      { path: 'ticket/resumen/:id', component: TicketResumen, canActivate: [AuthGuard] },
      { path: 'usuarios', component: UsersPage, canActivate: [AuthGuard] },
      { path: 'personal', component: PersonalList, canActivate: [AuthGuard] },
      { path: 'personal/nuevo', component: PersonalForm, canActivate: [AuthGuard] },
      { path: 'personal/editarpersonal/:id', component: PersonalForm, canActivate: [AuthGuard] },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    ],
  },
  { path: 'login', component: Login },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
];
