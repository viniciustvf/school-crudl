import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InputNumberModule } from 'primeng/inputnumber';
import { FormsModule } from '@angular/forms';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import { Button } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    FormsModule,
    Button,
    NgxMaskDirective, 
    NgxMaskPipe,
    DropdownModule,
    InputNumberModule
  ]
})
export class PersonModule { }
