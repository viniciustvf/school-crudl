import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Button } from 'primeng/button';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { DropdownModule } from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';

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
  ],
  providers: [provideNgxMask()]
})
export class CourseModule { }
