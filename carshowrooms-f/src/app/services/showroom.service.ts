import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Showroom } from '../models/showroom.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ShowroomService {
  private apiUrl = environment.apiUrl + '/showrooms';
  constructor(private http: HttpClient) { }

  getShowrooms(page: number = 0, size: number = 10, sort: string = 'name'): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort);
    
    return this.http.get<any>(`${this.apiUrl}`, { params });
  }

  getShowroomById(id: number): Observable<Showroom> {
    return this.http.get<Showroom>(`${this.apiUrl}/${id}`);
  }

  createShowroom(showroom: Showroom): Observable<Showroom> {
    return this.http.post<Showroom>(`${this.apiUrl}`, showroom);
  }

  updateShowroom(id: number, showroom: Partial<Showroom>): Observable<Showroom> {
    return this.http.put<Showroom>(`${this.apiUrl}/${id}`, showroom);
  }

  deleteShowroom(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}