import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./components/login/login.component";
import {UserListComponent} from "./components/user/user-list/user-list.component";
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {UserFormComponent} from "./components/user/user-form/user-form.component";
import {UserComponent} from "./components/user/user.component";

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'new-account', component: UserFormComponent, data: {name: 'New account'}},
  {
    path: 'user', component: UserComponent,
    children: [
      {path: '', redirectTo: '/user/list', pathMatch: 'full'},
      {path: 'list', component: UserListComponent, data: {name: 'user-list'}},
      {path: 'user-form', component: UserFormComponent, data: {name: 'Add'}},
      {path: 'user-edit/:id', component: UserFormComponent, data: {name: 'Edit'}}
    ]
  },
  {path: 'not-found', component: NotFoundComponent, pathMatch: 'full'},
  {path: '**', redirectTo: '/not-found', pathMatch: 'full'},
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes),
    CommonModule
  ]
})
export class AppRoutingModule {
}
