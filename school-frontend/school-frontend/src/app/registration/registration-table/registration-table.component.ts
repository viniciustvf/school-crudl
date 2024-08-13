import { Component, EventEmitter, Output } from '@angular/core';
import { Registration } from '../../models/registration';
import { RegistrationService } from '../services/registration.service';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-registration-table',
  standalone: true,
  imports: [TableModule, ButtonModule, CommonModule,],
  templateUrl: './registration-table.component.html',
  styleUrl: './registration-table.component.scss'
})
export class RegistrationTableComponent {

  @Output()
  public selectEvent = new EventEmitter<Registration>();

  @Output()
  public onDeleteEvent = new EventEmitter();

  registrations: Registration[] = [];

  constructor(private registrationService: RegistrationService, private messageService: MessageService) { }
  
  ngOnInit(): void {
    this.registrationService.listAll().subscribe((registrations) => {
      this.registrations = registrations
    });
  }

  onDelete(courseId: string) {
    this.onDeleteEvent.emit();
    this.registrationService.delete(courseId).subscribe(() => {
        this.registrationService.listAll().subscribe((registrations) => {
            this.registrations = registrations ?? undefined;
        });
        this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Matrícula excluída com sucesso.'
        });
    }, () => {
        this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: 'Erro ao excluir matrícula.'
        });
    });
  }

  onEdit(registration: Registration) {
    this.selectEvent.emit(registration);
  }

}
