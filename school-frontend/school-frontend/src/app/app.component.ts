import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ToastModule } from 'primeng/toast';
import { MenuComponent } from "./menu/menu/menu.component";
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ToastModule, MenuComponent, NgxMaskDirective, NgxMaskPipe],
  template: ` <app-menu></app-menu>
    <router-outlet/>
    <p-toast/>`,
})
export class AppComponent {
  title = 'school-frontend';
}
