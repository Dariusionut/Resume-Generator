import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {LoginComponent} from './components/login/login.component';
import {UserListComponent} from './components/user/user-list/user-list.component';
import {LoginFormComponent} from './components/login/login-form/login-form.component';
import {AppRoutingModule} from './app-routing.module';
import {RouterModule} from "@angular/router";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {HttpClientModule} from "@angular/common/http";
import {UserFormComponent} from './components/user/user-form/user-form.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ViewUserComponent} from './components/user/view-user/view-user.component';
import {UserComponent} from './components/user/user.component';
import {CustomDirectiveDirective} from './directives/custom-directive.directive';
import {MouseOverDirective} from "./directives/mouse-over.directive";
import {UserFilterComponent} from './components/user/user-list/user-filter/user-filter.component';
import {PaginationComponent} from './components/pagination/pagination.component';
import {HideAnimationDirective} from './directives/hide-animation.directive';
import {DatePipe} from "@angular/common";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    UserListComponent,
    LoginFormComponent,
    NotFoundComponent,
    UserFormComponent,
    ViewUserComponent,
    UserComponent,
    CustomDirectiveDirective,
    MouseOverDirective,
    UserFilterComponent,
    PaginationComponent,
    HideAnimationDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}
