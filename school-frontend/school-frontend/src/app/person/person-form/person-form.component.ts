import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { PersonService } from '../services/person.service';
import { Person } from '../../models/person';
import { MessageService } from 'primeng/api';
import { DateUtil } from '../../utils/date-util';
import { InputNumberModule } from 'primeng/inputnumber';
import { PersonType } from '../../models/enums/personType';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-person-form',
  standalone: true,
  imports: [FormsModule, CalendarModule, NgxMaskDirective, NgxMaskPipe, DropdownModule, InputNumberModule, CardModule, InputTextModule],
  templateUrl: './person-form.component.html',
  styleUrls: ['./person-form.component.scss'],
  providers: [provideNgxMask()]
})
export class PersonFormComponent implements OnInit {
  @ViewChild('form') form!: NgForm;

  @Input() 
  set person(person: Person | null) {
    if (person) {
      this.currentPerson = person;
      this.form.setValue({
        txtName: person.name,
        txtCpf: person.cpf,
        txtPassword: person.password,
        txtBirthDate: person.birthDate,
        txtPersonType: person.personType
      });
      this.selectedPersonType = person.personType;
    } else {
      this.currentPerson = null;
      this.form.resetForm();
    }
  }
  
  personTypes!: PersonType[];
  selectedPersonType: PersonType | undefined;
  currentPerson: Person | null = null;

  errorMessage: string = '';

  maxDate!: Date;

  constructor(private personService: PersonService, private messageService: MessageService) {}
  
  ngOnInit() {
    this.maxDate = new Date();
    this.personTypes = [
      PersonType.STUDENT,
      PersonType.TEACHER
    ];
  }

  onSave(form: NgForm) {
    if (form.valid) {
      const personData: Person = {
        id: this.currentPerson?.id,
        name: form.value.txtName,
        cpf: form.value.txtCpf,
        password: form.value.txtPassword,
        birthDate: form.value.txtBirthDate,
        personType: this.selectedPersonType ?? PersonType.STUDENT
      };
      if (personData.id) {
        this.updatePerson(personData, form);
      } else {
        this.insertPerson(personData, form);
      }
    } else {
      this.errorMessage = '';
      const controls = form.controls;

      if (controls['txtName']?.invalid) {
        this.errorMessage += 'Nome é obrigatório. ';
      }
      if (controls['txtCpf']?.invalid) {
        this.errorMessage += 'CPF é obrigatório. ';
      }
      if (controls['txtBirthDate']?.invalid) {
        this.errorMessage += 'Data de nascimento é obrigatória. ';
      }
      this.messageService.add({severity: 'error', summary: 'Error', detail: this.errorMessage});
    }
  }

  insertPerson(person: Person, form: NgForm) {
    this.personService.insert(person).subscribe({
      next: () => {
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Pessoa salva com sucesso.'});
        form.resetForm();
        this.currentPerson = null;
      },
      error: () => {
        this.messageService.add({severity: 'error', summary: 'Error', detail: 'Erro ao salvar pessoa. Revise os dados inseridos e tente novamente.'});
      }
    });
  }

  updatePerson(person: Person, form: NgForm) {
    this.personService.update(person.id!, person).subscribe({
      next: () => {
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Pessoa atualizada com sucesso.'});
        form.resetForm();
        this.currentPerson = null;
      },
      error: () => {
        this.messageService.add({severity: 'error', summary: 'Error', detail: 'Erro ao atualizar pessoa. Revise os dados inseridos e tente novamente.'});
      }
    });
  }

  editPerson(person: Person) {
    this.currentPerson = person;
    this.form.setValue({
      txtName: person.name,
      txtCpf: person.cpf,
      txtBirthDate: DateUtil.stringToDate(person.birthDate, 'yyyy/MM/dd')!,
    });
  }

  capitalizeWords(form: NgForm) {
    const nameControl = form.controls['txtName'];
    if (nameControl) {
      let filteredValue = nameControl.value.replace(/[^a-zA-Z\s]/g, '');
      
      const words = filteredValue.split(' ');
      for (let i = 0; i < words.length; i++) {
        words[i] = words[i].charAt(0).toUpperCase() + words[i].slice(1).toLowerCase();
      }
      
      nameControl.setValue(words.join(' '), { emitEvent: false });
    }
  }
}