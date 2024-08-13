import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [RouterLink, ButtonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {
  public showMenu: boolean = false;

  constructor(private router: Router) {}
  
  ngOnInit(): void {
    this.checkRoute();
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.checkRoute();
      }
    });
  }

  private checkRoute(): void {
    const currentRoute = this.router.url;
    this.showMenu = currentRoute !== '/login';
  }
}
