import { Component, Input, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { CourseService } from '../services/course.service';
import { Course } from '../../models/course';
import { MessageService } from 'primeng/api';
import { DateUtil } from '../../utils/date-util';
import { InputNumberModule } from 'primeng/inputnumber';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { RegistrationService } from '../../registration/services/registration.service';

@Component({
  selector: 'app-course-form',
  standalone: true,
  imports: [FormsModule, CalendarModule, NgxMaskDirective, NgxMaskPipe, DropdownModule, InputNumberModule, CardModule, InputTextModule],
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss'],
  providers: [provideNgxMask()]
})
export class CourseFormComponent {
  @ViewChild('form') form!: NgForm;

  @Input() 
  set course(course: Course | null) {
    if (course) {
      this.currentCourse = course;
      this.form.setValue({
        txtName: course.name,
        txtTotalVacancies: course.totalVacancies,
        txtInit: new Date(course.init),
        txtEnd: new Date(course.end),
        minimumStudentAge: course.minimumStudentAge
      });
      this.selectedAge = course.minimumStudentAge;
    } else {
      this.currentCourse = null;
      this.form.resetForm();
    }
  }
  
  ages!: any[];
  selectedAge: number | undefined;
  currentCourse: Course | null = null;

  errorMessage: string = '';

  constructor(private courseService: CourseService, private registrationService: RegistrationService, private messageService: MessageService) {
    this.ages = Array.from({ length: 130 }, (v, k) => ({ label: k + 1, value: k + 1 }));
  }

  onSave(form: NgForm) {
    if (form.valid) {
      const courseData: Course = {
        id: this.currentCourse?.id,
        name: form.value.txtName,
        totalVacancies: form.value.txtTotalVacancies,
        init: form.value.txtInit,
        end: form.value.txtEnd,
        minimumStudentAge: Number(this.selectedAge)
      };
      if (courseData.id) {
        this.updateCourse(courseData, form);
      } else {
        this.insertCourse(courseData, form);
      }
    } else {
      this.errorMessage = '';
      const controls = form.controls;

      if (controls['txtName']?.invalid) {
        this.errorMessage += 'Nome é obrigatório. ';
      }
      if (controls['txtTotalVacancies']?.invalid) {
        this.errorMessage += 'Total de vagas é obrigatório. ';
      }
      if (controls['txtInit']?.invalid) {
        this.errorMessage += 'Data de início é obrigatória. ';
      }
      if (controls['txtEnd']?.invalid) {
        this.errorMessage += 'Data de término é obrigatória. ';
      }
      if (controls['txtMinimumStudentAge']?.invalid) {
        this.errorMessage += 'Idade mínima é obrigatório. ';
      }
      this.messageService.add({severity: 'error', summary: 'Error', detail: this.errorMessage});
    }
  }

  insertCourse(course: Course, form: NgForm) {
    this.courseService.insert(course).subscribe({
      next: () => {
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Curso salvo com sucesso.'});
        form.resetForm();
        this.currentCourse = null;
      },
      error: (errorResponse) => {
        this.messageService.add({severity: 'error', summary: 'Error', detail: 'Erro ao salvar curso. ' + errorResponse.error.message});
      }
    });
  }

  updateCourse(course: Course, form: NgForm) {
    this.registrationService.findByCourseId(String(course.id)).subscribe((it) => {
      if (it.length > course.totalVacancies) {
        this.messageService.add({
          severity: 'warn',
          summary: 'Atenção',
          detail: 'Total de vagas não pode ser menor que total de vagas já preenchidas.'
        });
        return;
      } else {
        this.courseService.update(course.id!, course).subscribe({
          next: () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Success',
              detail: 'Curso atualizado com sucesso.'
            });
            form.resetForm();
            this.currentCourse = null;
          },
          error: () => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Erro ao atualizar curso.'
            });
          }
        });
      }
    });
  }

  editCourse(course: Course) {
    this.currentCourse = course;
    this.form.setValue({
      txtName: course.name,
      txtTotalVacancies: course.totalVacancies,
      txtInit: DateUtil.stringToDate(course.init, 'yyyy/MM/dd')!,
      txtEnd: DateUtil.stringToDate(course.end, 'yyyy/MM/dd')!,
      minimumStudentAge: course.minimumStudentAge
    });
    this.selectedAge = course.minimumStudentAge;
  }
}