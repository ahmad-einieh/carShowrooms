import { Routes } from '@angular/router';
import { ShowroomListComponent } from './components/showroom/showroom-list/showroom-list.component';
import { ShowroomCreateComponent } from './components/showroom/showroom-create/showroom-create.component';
import { ShowroomDetailsComponent } from './components/showroom/showroom-details/showroom-details.component';
import { ShowroomEditComponent } from './components/showroom/showroom-edit/showroom-edit.component';
import { CarListComponent } from './components/car/car-list/car-list.component';
import { CarCreateComponent } from './components/car/car-create/car-create.component';
import { ErrorComponent } from './components/shared/error/error.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './helpers/auth.guard';
import { RegisterComponent } from './components/register/register.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: '', redirectTo: '/showrooms', pathMatch: 'full' },
    { path: 'showrooms', component: ShowroomListComponent,canActivate: [AuthGuard] },
    { path: 'showrooms/create', component: ShowroomCreateComponent,canActivate: [AuthGuard] },
    { path: 'showrooms/:id', component: ShowroomDetailsComponent ,canActivate: [AuthGuard]},
    { path: 'showrooms/:id/edit', component: ShowroomEditComponent ,canActivate: [AuthGuard]},
    { path: 'cars', component: CarListComponent,canActivate: [AuthGuard] },
    { path: 'cars/create', component: CarCreateComponent,canActivate: [AuthGuard] },
    { path: '**', component: ErrorComponent }
];
