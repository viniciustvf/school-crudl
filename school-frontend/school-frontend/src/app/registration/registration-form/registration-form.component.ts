import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { RegistrationService } from '../services/registration.service';
import { Registration } from '../../models/registration';
import { MessageService } from 'primeng/api';
import { DateUtil } from '../../utils/date-util';
import { InputNumberModule } from 'primeng/inputnumber';
import { Person } from '../../models/person';
import { Course } from '../../models/course';
import { CourseService } from '../../course/services/course.service';
import { PersonService } from '../../person/services/person.service';
import { forkJoin } from 'rxjs';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-registration-form',
  standalone: true,
  imports: [FormsModule, CalendarModule, NgxMaskDirective, NgxMaskPipe, DropdownModule, InputNumberModule, CardModule],
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.scss'],
  providers: [provideNgxMask()]
})
export class RegistrationFormComponent implements OnInit {
  @ViewChild('form') form!: NgForm;

  @Input() 
  set registration(registration: Registration | null) {
    if (registration) {
      this.currentRegistration = registration;
      this.form.setValue({
        id: registration?.id,
        txtRegistrationDate: registration.registrationDate,
        txtPerson: registration.person,
        txtCourse: registration.course
      });
    } else {
      this.currentRegistration = null;
      this.form.resetForm();
    }
  }
  
  selectedPerson!: Person;
  selectedCourse!: Course;

  persons!: Person[];
  courses!: Course[];

  currentRegistration: Registration | null = null;
  errorMessage: string = '';

  constructor(
    private registrationService: RegistrationService, 
    private courseService: CourseService, 
    private personService: PersonService,
    private messageService: MessageService) {}
  
  ngOnInit(): void {
    this.personService.listAll().subscribe((it) => {
      this.persons = it
    });
    this.courseService.listAll().subscribe((it) => 
      this.courses = it
    )
  }

  onSave(form: NgForm) {
    if (form.valid) {
      const registrationData: Registration = {
        id: this.currentRegistration?.id,
        personId: this.selectedPerson?.id,
        personName: this.selectedPerson?.name,
        courseId: this.selectedCourse?.id,
        courseName: this.selectedCourse.name,
        registrationDate: form.value.txtRegistrationDate,
      };
      if (registrationData.id) {
        this.updateRegistration(registrationData, form);
      } else {
        this.insertRegistration(registrationData, form);
      }
    } else {
      this.errorMessage = '';
      const controls = form.controls;

      if (controls['txtPerson']?.invalid) {
        this.errorMessage += 'Pessoa é obrigatório. ';
      }
      if (controls['txtCourse']?.invalid) {
        this.errorMessage += 'Curso é obrigatório. ';
      }
      if (controls['txtRegistrationDate']?.invalid) {
        this.errorMessage += 'Data de matrícula é obrigatória. ';
      }
      this.messageService.add({severity: 'error', summary: 'Error', detail: this.errorMessage});
    }
  }

  insertRegistration(registration: Registration, form: NgForm) {
    this.registrationService.insert(registration).subscribe({
      next: () => {
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Matrícula salva com sucesso.'});
        form.resetForm();
        this.currentRegistration = null;
        this.ngOnInit();
      },
      error: (errorResponse) => {
        const errorMessage = errorResponse.error.message || 'Erro ao salvar matrícula.';
        this.messageService.add({severity: 'error', summary: 'Erro', detail: errorMessage});
      }
    });
  }

  updateRegistration(registration: Registration, form: NgForm) {
    this.registrationService.update(registration.id!, registration).subscribe({
      next: () => {
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Matrícula atualizada com sucesso.'});
        form.resetForm();
        this.currentRegistration = null;
        this.ngOnInit();
      },
      error: (errorResponse) => {
        const errorMessage = errorResponse.error.message || 'Erro ao atualizar matrícula.';
        this.messageService.add({severity: 'error', summary: 'Erro', detail: errorMessage});
      }
    });
  }

  editRegistration(registration: Registration) {
    this.currentRegistration = registration;
    forkJoin({
      person: this.personService.findById(String(registration.personId)),
      course: this.courseService.findById(String(registration.courseId))
    }).subscribe(({ person, course }) => {
      this.form.setValue({
        txtPerson: person,
        txtCourse: course,
        txtRegistrationDate: DateUtil.stringToDate(registration.registrationDate, 'yyyy/MM/dd')!,
      });
      this.selectedCourse = course
      this.selectedPerson = person
    });
  };
}