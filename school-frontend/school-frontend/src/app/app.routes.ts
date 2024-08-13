import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login/login.component';
import { NgModule } from '@angular/core';
import { HomeComponent } from './home/home/home.component';
import { RegistrationComponent } from './registration/registration/registration.component';
import { CourseComponent } from './course/course/course.component';
import { PersonComponent } from './person/person/person.component';

export const routes: Routes = [
    { path: 'person', component: PersonComponent },
    { path: 'course', component: CourseComponent },
    { path: 'registration', component: RegistrationComponent },
    { path: 'login', component: LoginComponent },
    { path: '', component: HomeComponent },
  ];
  
  @NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
  })
  export class AppRoutingModule {}
