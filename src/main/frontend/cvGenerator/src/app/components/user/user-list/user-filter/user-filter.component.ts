import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {NgForm} from "@angular/forms";
import {UserFilter} from "../../../../models/util/user-filter";
import {Router} from "@angular/router";
import {DataObject} from "../../../../models/util/data-object";
import {User} from "../../../../models/user";
import {UserService} from "../../../../services/user.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Observable} from "rxjs";

@Component({
  selector: 'app-user-filter',
  templateUrl: './user-filter.component.html',
  styleUrls: ['./user-filter.component.scss']
})
export class UserFilterComponent implements OnInit {

  constructor(private router: Router, private userService: UserService) {
  }

  @Output('filterParams') params = new EventEmitter<UserFilter>;

  @Output('userObs') userEmitter = new EventEmitter<Observable<DataObject<User>>>;

  ngOnInit(): void {
  }

  public resetForm(form: NgForm): Observable<DataObject<User>> {
    form.reset();
    localStorage.removeItem('userParams')

    return this.userService.onReset();
  }

  public searchFilter(form: NgForm): void {
    this.params.emit(form.value);
  }

}
