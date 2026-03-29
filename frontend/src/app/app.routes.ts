import { Routes } from '@angular/router';
import { SuperAdminGuard } from './auth/route-guard';
import { CreateuserComponent } from './createuser/createuser.component';
import { ForgetPassword } from './forget-password/forget-password';
import { HomeComponent } from './home/home.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { ProfileView } from './profile-view/profile-view';
import { QuariesComponent } from './quaries/quaries.component';

export const routes: Routes = [
  { path: 'login', component: LoginPageComponent },
  { path: 'forgotpassword', component: ForgetPassword },
  {
    path: '',
    children: [
      {
        path: 'home',
        component: HomeComponent,
        canActivate: [SuperAdminGuard],
      },
      {
        path: 'quaries',
        component: QuariesComponent,
        canActivate: [SuperAdminGuard],
      },
      {
        path: 'createuser',
        component: CreateuserComponent,
        canActivate: [SuperAdminGuard],
      },
      {
        path: 'profile',
        component: ProfileView,
        canActivate: [SuperAdminGuard],
      },
    ],
  },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login', pathMatch: 'full' },
];
