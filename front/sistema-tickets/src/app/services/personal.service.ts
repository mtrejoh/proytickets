import { getApiUrl } from './../config/api-config';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PersonalModelTs } from '../models/personal.model';
import { DatPersonal } from '../models/datpersonal.model';



@Injectable({
  providedIn: 'root',
})
export class PersonalServiceTs {
  private listar = getApiUrl('personalistar');
  private lstPersonal = getApiUrl('personalActivo');
  private createUsr = getApiUrl('userCreate');
  private changeRol = getApiUrl('userChangeRol');
  private changeStatus = getApiUrl('userChgStatus');
  private guardarPersonal = getApiUrl('savePersonal');
  private actualizarPersonal = getApiUrl('updatePersonal');
  private actualizarStPersonal = getApiUrl('updateStPersonal');

  constructor(private http: HttpClient) {}

  getPersonalActivo(): Observable<PersonalModelTs[]> {
    return this.http.get<PersonalModelTs[]>(this.lstPersonal, {});
  }

  createUsuario(
    personal: Partial<PersonalModelTs> | Record<string, any>,
  ): Observable<PersonalModelTs> {
    return this.http.put<PersonalModelTs>(this.createUsr, personal);
  }

  cambiarRol(personal: Partial<PersonalModelTs> | Record<string, any>): Observable<any> {
    return this.http.post<any>(this.changeRol, personal);
  }

  cambiarStatus(
    personal: Partial<PersonalModelTs> | Record<string, any>,
  ): Observable<PersonalModelTs> {
    return this.http.post<any>(this.changeStatus, personal);
  }

  listarPersonal(): Observable<DatPersonal[]> {
    return this.http.get<DatPersonal[]>(this.listar);
  }

  eliminarPersonal(cvePersonal: string): Observable<any> {
    return this.http.put(`/personal/estatus/${cvePersonal}`, {
      estatus: 0,
    });
  }

  guardar(personal: Partial<DatPersonal> | Record<string, any>): Observable<DatPersonal> {
    return this.http.post<any>(this.guardarPersonal, personal);
  }

  actualizar(
    personal: Partial<DatPersonal> | Record<string, any>,
  ): Observable<DatPersonal> {
    return this.http.post<any>(this.actualizarPersonal, personal);
  }

  actualizarStatus(
    personal: Partial<DatPersonal> | Record<string, any>,
  ): Observable<DatPersonal> {
    return this.http.post<any>(this.actualizarStPersonal, personal);
  }
}
