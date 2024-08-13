import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, catchError, Observable, Subject, tap } from 'rxjs';
import { Course } from '../../models/course';
import { Registration } from '../../models/registration';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private baseUrl = 'http://localhost:8080/course';
  private courseSubject = new BehaviorSubject<Course[]>([]);

  constructor(private http: HttpClient) { }

  findById(id: string): Observable<Course> {
    return this.http.get<Course>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }

  insert(course: Course): Observable<Course> {
    return this.http.post<Course>(this.baseUrl, course, { withCredentials: true }).pipe(tap(() => {this.listAll()}));
  }

  update(id: string, course: Course): Observable<Course> {
    return this.http.put<Course>(`${this.baseUrl}/${id}`, course, { withCredentials: true }).pipe(tap(() => {this.listAll()}));
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }

  listAll(): Observable<Course[]> {
    this.http.get<Course[]>(this.baseUrl, { withCredentials: true }).pipe(
      tap((courses) => this.courseSubject.next(courses)),
      catchError((error: HttpErrorResponse) => {
        console.error('Ocorreu um erro na requisição:', error);
        return [];
      })
    ).subscribe();
    return this.courseSubject.asObservable();
  }

  countTotalRegistrationsById(id: string): Observable<Number> {
    return this.http.get<Number>(`${this.baseUrl}/count-registrations-by-id/${id}`, { withCredentials: true });
  }
}