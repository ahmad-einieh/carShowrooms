import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Car } from '../models/car.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CarService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getCars(page: number = 0, size: number = 10, filters: Record<string, any> = {}): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    // Add dynamic filters
    Object.keys(filters).forEach(key => {
      if (filters[key] !== null && filters[key] !== '') {
        params = params.set(key, filters[key]);
      }
    });
    
    return this.http.get<any>(`${this.apiUrl}/cars`, { params });
  }

  getCarsByShowroom(showroomId: number, page: number = 0, size: number = 10): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<any>(`${this.apiUrl}/showrooms/${showroomId}/cars`, { params });
  }

  createCar(showroomId: number, car: Car): Observable<Car> {
    return this.http.post<Car>(`${this.apiUrl}/showrooms/${showroomId}/cars`, car);
  }
}