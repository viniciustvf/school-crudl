import { Component, ViewChild } from '@angular/core';
import { RegistrationFormComponent } from "../registration-form/registration-form.component";
import { RegistrationTableComponent } from "../registration-table/registration-table.component";
import { Registration } from '../../models/registration';

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [RegistrationFormComponent, RegistrationTableComponent],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.scss'
})
export class RegistrationComponent {
  @ViewChild(RegistrationFormComponent) registrationFormComponent!: RegistrationFormComponent;

  onSelectRegistration(registration: Registration) {
    this.registrationFormComponent.editRegistration(registration);
  }

  onDeleteEvent() {
    this.registrationFormComponent.form.resetForm();
    this.registrationFormComponent.registration = null;
  }
}
