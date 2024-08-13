import { AfterViewInit, Component, EventEmitter, OnInit, Output } from '@angular/core';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { CourseService } from '../services/course.service';
import { Course } from '../../models/course';
import { CommonModule } from '@angular/common';
import { MessageService } from 'primeng/api';
import { CardModule } from 'primeng/card';
import { RegistrationService } from '../../registration/services/registration.service';

@Component({
  selector: 'app-course-table',
  standalone: true,
  imports: [TableModule, ButtonModule, CommonModule, CardModule],
  templateUrl: './course-table.component.html',
  styleUrls: ['./course-table.component.scss']
})
export class CourseTableComponent implements OnInit{
  
  @Output()
  public selectEvent = new EventEmitter<Course>();

  @Output()
  public onDeleteEvent = new EventEmitter();
  
  courses: Course[] = [];

  constructor(private courseService: CourseService, private messageService: MessageService, private registrationService: RegistrationService) { }
  
  ngOnInit(): void {
    this.courseService.listAll().subscribe((courses) => {
      this.courses = courses
    });
  }

  onDelete(courseId: string) {
    this.onDeleteEvent.emit();
    this.registrationService.findByCourseId(courseId).subscribe((totalRegistrations) => {
      if (totalRegistrations.length > 0) {
        this.messageService.add({
          severity: 'warn',
          summary: 'Atenção',
          detail: 'Não é possível excluir um curso com matrículas ativas.'
        });
        return;
      } else {
        this.courseService.delete(courseId).subscribe(() => {
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Curso deletado com sucesso.'
          });
          this.courseService.listAll().subscribe((courses) => {
            this.courses = courses;
          });
        }, () => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Erro ao deletar curso.'
          });
        });
      }
    });
  }
  
  onEdit(course: Course) {
    this.selectEvent.emit(course);
  }
}