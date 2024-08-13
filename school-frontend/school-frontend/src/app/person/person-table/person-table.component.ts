import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { PersonService } from '../services/person.service';
import { Person } from '../../models/person';
import { CommonModule } from '@angular/common';
import { RegistrationService } from '../../registration/services/registration.service';
import { MessageService } from 'primeng/api';
import { CpfPipe } from "../../pipe/cpf.pipe";

@Component({
  selector: 'app-person-table',
  standalone: true,
  imports: [TableModule, ButtonModule, CommonModule, CpfPipe],
  templateUrl: './person-table.component.html',
  styleUrls: ['./person-table.component.scss']
})
export class PersonTableComponent implements OnInit{
  
  @Output()
  public selectEvent = new EventEmitter<Person>();

  @Output()
  public onDeleteEvent = new EventEmitter();
  
  persons: Person[] = [];

  constructor(private personService: PersonService, private registrationService: RegistrationService, private messageService: MessageService) { }
  
  ngOnInit(): void {
    this.personService.listAll().subscribe((persons) => {
      this.persons = persons
    });
  }

  onDelete(personId: string) {
    this.onDeleteEvent.emit();
    this.registrationService.findByPersonId(personId).subscribe((it) => {
      if (it.length > 0) {
        this.messageService.add({severity: 'warn', summary: 'Atenção', detail: 'Pessoa já possui matrícula em um curso.'});
      } else {
        this.personService.delete(personId).subscribe(() => {
          this.messageService.add({severity: 'success', summary: 'Sucesso', detail: 'Pessoa excluída com sucesso.'});
          this.ngOnInit();
        });
      }
    })
  }

  onEdit(person: Person) {
    this.selectEvent.emit(person);
  }
}