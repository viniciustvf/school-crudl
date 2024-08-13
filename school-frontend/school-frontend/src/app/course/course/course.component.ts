import { Component, ViewChild } from '@angular/core';
import { CourseFormComponent } from "../course-form/course-form.component";
import { CourseTableComponent } from "../course-table/course-table.component";
import { Course } from '../../models/course';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-course',
  standalone: true,
  imports: [CourseFormComponent, CourseTableComponent, CardModule],
  templateUrl: './course.component.html',
  styleUrl: './course.component.scss'
})
export class CourseComponent {
  @ViewChild(CourseFormComponent) courseFormComponent!: CourseFormComponent;

  onSelectCourse(course: Course) {
    this.courseFormComponent.editCourse(course);
  }

  onDeleteEvent() {
    this.courseFormComponent.form.resetForm();
    this.courseFormComponent.currentCourse = null;
  }
}
