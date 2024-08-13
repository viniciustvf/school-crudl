import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import { Button } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';
import { ConfirmDialogModule } from 'primeng/confirmdialog';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    FormsModule,
    Button,
    NgxMaskDirective, 
    NgxMaskPipe,
    DropdownModule,
    InputNumberModule,
    ConfirmDialogModule
  ]
})
export class RegistrationModule { }
