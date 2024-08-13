import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, catchError, Observable, Subject, tap } from 'rxjs';
import { Registration } from '../../models/registration';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private baseUrl = 'http://localhost:8080/registration';
  private registrationSubject = new BehaviorSubject<Registration[]>([]);

  constructor(private http: HttpClient) { }

  findById(id: string): Observable<Registration> {
    return this.http.get<Registration>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }

  insert(registration: Registration): Observable<Registration> {
    return this.http.post<Registration>(this.baseUrl, registration, { withCredentials: true }).pipe(tap(() => {this.listAll()}));
  }

  update(id: string, registration: Registration): Observable<Registration> {
    return this.http.put<Registration>(`${this.baseUrl}/${id}`, registration, { withCredentials: true }).pipe(tap(() => {this.listAll()}));
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }

  listAll(): Observable<Registration[]> {
    this.http.get<Registration[]>(this.baseUrl, { withCredentials: true }).pipe(
      tap((registrations) => this.registrationSubject.next(registrations)),
      catchError((error: HttpErrorResponse) => {
        console.error('Ocorreu um erro na requisição:', error);
        return [];
      })
    ).subscribe();
    return this.registrationSubject.asObservable();
  }

  findByPersonId(personId: string): Observable<Registration[]> {
    return this.http.get<Registration[]>(`${this.baseUrl}/find-by-person-id/${personId}`, { withCredentials: true }).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Ocorreu um erro na requisição:', error);
        throw error;
      })
    );
  }

  findByCourseId(courseId: string): Observable<Registration[]> {
    return this.http.get<Registration[]>(`${this.baseUrl}/find-by-course-id/${courseId}`, { withCredentials: true }).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Ocorreu um erro na requisição:', error);
        throw error;
      })
    );
  }
}