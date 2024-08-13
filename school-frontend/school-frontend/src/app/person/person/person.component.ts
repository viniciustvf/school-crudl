import { Component, ViewChild } from '@angular/core';
import { PersonFormComponent } from "../person-form/person-form.component";
import { PersonTableComponent } from "../person-table/person-table.component";
import { Person } from '../../models/person';

@Component({
  selector: 'app-person',
  standalone: true,
  imports: [PersonFormComponent, PersonTableComponent],
  templateUrl: './person.component.html',
  styleUrl: './person.component.scss'
})
export class PersonComponent {
  @ViewChild(PersonFormComponent) personFormComponent!: PersonFormComponent;

  onSelectPerson(person: Person) {
    this.personFormComponent.editPerson(person);
  }

  onDeleteEvent() {
    this.personFormComponent.form.resetForm();
    this.personFormComponent.currentPerson = null;
  }
}
