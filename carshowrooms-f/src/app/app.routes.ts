import { Routes } from '@angular/router';
import { ShowroomListComponent } from './components/showroom/showroom-list/showroom-list.component';
import { ShowroomCreateComponent } from './components/showroom/showroom-create/showroom-create.component';
import { ShowroomDetailsComponent } from './components/showroom/showroom-details/showroom-details.component';
import { ShowroomEditComponent } from './components/showroom/showroom-edit/showroom-edit.component';
import { CarListComponent } from './components/car/car-list/car-list.component';
import { CarCreateComponent } from './components/car/car-create/car-create.component';
import { ErrorComponent } from './components/shared/error/error.component';

export const routes: Routes = [
    { path: '', redirectTo: '/showrooms', pathMatch: 'full' },
    { path: 'showrooms', component: ShowroomListComponent },
    { path: 'showrooms/create', component: ShowroomCreateComponent },
    { path: 'showrooms/:id', component: ShowroomDetailsComponent },
    { path: 'showrooms/:id/edit', component: ShowroomEditComponent },
    { path: 'cars', component: CarListComponent },
    { path: 'cars/create', component: CarCreateComponent },
    { path: '**', component: ErrorComponent }
];
