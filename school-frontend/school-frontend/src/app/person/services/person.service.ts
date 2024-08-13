import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, catchError, Observable, Subject, tap } from 'rxjs';
import { Person } from '../../models/person';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  private baseUrl = 'http://localhost:8080/person';
  private personSubject = new BehaviorSubject<Person[]>([]);

  constructor(private http: HttpClient) { }

  findById(id: string): Observable<Person> {
    return this.http.get<Person>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }

  insert(person: Person): Observable<Person> {
    return this.http.post<Person>(this.baseUrl, person, { withCredentials: true }).pipe(tap(() => {this.listAll()}));
  }

  update(id: string, person: Person): Observable<Person> {
    return this.http.put<Person>(`${this.baseUrl}/${id}`, person, { withCredentials: true }).pipe(tap(() => {this.listAll()}));
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }

  listAll(): Observable<Person[]> {
    this.http.get<Person[]>(this.baseUrl, { withCredentials: true }).pipe(
      tap((persons) => this.personSubject.next(persons)),
      catchError((error: HttpErrorResponse) => {
        console.error('Ocorreu um erro na requisição:', error);
        return [];
      })
    ).subscribe();
    return this.personSubject.asObservable();
  }
}