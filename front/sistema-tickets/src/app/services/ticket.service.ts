import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TicketModelTs } from '../models/ticket.model';
import { getApiUrl } from '../config/api-config';
import { TicketSegModelTs } from '../models/ticketseg.model';

@Injectable({
  providedIn: 'root',
})
export class TicketServiceTs {
  private linkDashBoard = getApiUrl('linkDashBoard');
  private listUrl = getApiUrl('listar');
  private ticketUrl = getApiUrl('tickets');
  private ticketCancel = getApiUrl('ticketCancel');
  private segumientoTicket = getApiUrl('ticketsSeg');
  private ticketClose = getApiUrl('ticketClose');
  private ticketTomar = getApiUrl('ticketTomar');

  constructor(private http: HttpClient) {}

  /**
   * Consumimos el servicio para el Dashboard
   * @param id 
   * @returns 
   */
  getDashboard(): Observable<any[]> {
    return this.http.get<any[]>(this.linkDashBoard);
  }

  getTickets(pagActual: number, numRegistros: number): Observable<TicketModelTs[]> {
    const params = new HttpParams()
      .set('pagina', pagActual.toString())
      .set('registros', numRegistros.toString());
    return this.http.get<TicketModelTs[]>(this.listUrl, { params });
  }

  getTicketById(id: string): Observable<TicketModelTs> {
    return this.http.get<TicketModelTs>(`${this.ticketUrl}/${id}`);
  }

  createTicket(ticket: Partial<TicketModelTs> | Record<string, any>): Observable<TicketModelTs> {
    return this.http.post<TicketModelTs>(this.ticketUrl, ticket);
  }

  /**
   * Metodo para cancelar un ticket
   *
   * @param id
   * @returns
   */
  deleteTicket(id: string | number, descripcion: string, cveUsuario: string): Observable<void> {
    return this.http.delete<void>(`${this.ticketCancel}/${id}`, {
      body: { descripcion, cveUsuario },
    });
  }

  getTicketSegById(id: string): Observable<TicketSegModelTs[]> {
    return this.http.get<TicketSegModelTs[]>(`${this.segumientoTicket}/${id}`);
  }

  createSegTicket(
    segTicket: Partial<TicketSegModelTs> | Record<string, any>,
  ): Observable<TicketSegModelTs> {
    return this.http.post<TicketSegModelTs>(this.segumientoTicket, segTicket);
  }

  /**
   * Metodo para Cerrar un ticket
   *
   * @param id
   * @returns
   */
  closeTicket(
    id: string | number,
    descripcion: string,
    cveUsuario: string,
    estatus: string,
  ): Observable<void> {
    return this.http.post<void>(`${this.ticketClose}/${id}`, { descripcion, cveUsuario, estatus });
  }

  tomarTicket(id: string | number, cveUsuario: string): Observable<void> {
    return this.http.post<void>(`${this.ticketTomar}/${id}/${cveUsuario}`, {});
  }
}
