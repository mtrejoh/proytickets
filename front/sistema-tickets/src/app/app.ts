import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TicketList } from './pages/tickets/ticket-list/ticket-list'; // <-- Importar

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
 title = 'sistema-tickets'
}
