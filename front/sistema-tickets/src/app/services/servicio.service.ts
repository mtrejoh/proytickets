import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { getApiUrl } from '../config/api-config';
import { UserServiceModelTs } from '../models/usuarioservice.model';
import { RolModelTs } from '../models/roles.model';

@Injectable({
  providedIn: 'root',
})
export class ServicioServiceTs {
  private userServicio = getApiUrl('userService');
  private userAtender = getApiUrl('userAtender');
  private getRolService = getApiUrl('roles');

  constructor(private http: HttpClient) {}

  getUserService(): Observable<UserServiceModelTs[]> {
    return this.http.get<UserServiceModelTs[]>(this.userServicio);
  }

  sendAtener(asignado: string, idTicket: string): Observable<any> {
    return this.http.post<any>(this.userAtender, {
      ticket: idTicket,
      usuario: asignado,
    });
  }


  getRol(): Observable<RolModelTs[]> {
    return this.http.get<RolModelTs[]>(this.getRolService);
  }
}
