import {Component, OnDestroy, OnInit,} from '@angular/core';
import {UserService} from "../../../services/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {NgForm, NgModel} from "@angular/forms";
import {User} from "../../../models/user";
import {Gender} from "../../../models/gender";
import {Role} from "../../../models/role";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent implements OnInit, OnDestroy {

  title: string = this.activatedRoute.snapshot['data']['name'];
  isAddForm: boolean = this.title === 'Add';
  isNewAccountForm: boolean = this.title === 'New account';

  userById?: User;

  userId?: number;

  genders: Gender[] = [{id: 1, genderName: 'Male'}, {id: 2, genderName: 'Female'}];
  roles: Role[] = [{id: 1, roleName: 'Admin'}, {id: 2, roleName: 'User'}];

  userRole?: Role;

  constructor(private userService: UserService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private datePipe: DatePipe) {
  }

  ngOnDestroy(): void {
    this.userService.userId = undefined;
  }


  ngOnInit(): void {
    this.findUserById();
  }

  findUserById(): void {
    this.userId = this.activatedRoute.snapshot.params['id'];
    if (this.userId !== undefined) {
      this.userService.findUserById(this.userId)
        .subscribe({
          next: data => {
            this.userById = data;
            this.userRole = data.userSecurity.roles[0];
            console.log('userById', this.userById)
          },
          error: err => console.error(err)
        })
    }
  }

  errorMsg?: string;

  showErrors: boolean = false;

  public submitForm(userForm: NgForm): void {

    const date: string = userForm.value['userDetails']['dob'];
    userForm.value['userDetails']['dob'] = this.datePipe.transform(date, 'dd-MM-yyyy');

    if (userForm.valid) {
      this.userService.addOrEdit(userForm.value, this.isAddForm || this.isNewAccountForm).subscribe({
        next: data => {
          let msg;
          if (this.isAddForm) {
            msg = `Successfully added user: ${data.userDetails.lastName} ${data.userDetails.firstName}!`;
          } else if (this.isNewAccountForm) {
            msg = `Account was successfully created!<br>You can login using your credentials!`;
          } else {
            msg = `Successfully edited user: ${data.userDetails.lastName} ${data.userDetails.firstName}!`;
          }

          localStorage.setItem('redirectMsg', msg);
          localStorage.removeItem('userParams');
          this.router.navigateByUrl(this.isNewAccountForm ? '/login' : '/user/list');
        },
        error: e => {
          console.log('Error: ', e);
          if (this.isAddForm) {
            this.errorMsg = 'Cannot add user!';
            this.router.navigateByUrl("user/user-form");
          } else if (this.isNewAccountForm) {
            this.errorMsg = 'Cannot create account!!';
            this.router.navigateByUrl("/new-account");
          } else {
            this.errorMsg = 'Cannot update ' + this.userById?.userDetails.lastName + ' ' + this.userById?.userDetails.firstName + '!';
            this.router.navigateByUrl('/user/user-edit/' + this.userById?.id);
          }

        }
      });
    } else {
      this.showErrors = true;
      alert('Invalid form!')
    }
  }

  public validateEmail(email: NgModel): void {
    const rgxp =
      /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
    if (!email.value.match(rgxp)) {
      email.control.setErrors({invalid: true})
    }
  }

  public validateDob(dob: NgModel): void {
    console.log(this.datePipe.transform(dob.value))
    console.log(typeof this.datePipe.transform(dob.value))


  }

  public validatePassword(password: NgModel): void {
    if (password.value.length < 6) {
    }
  }

}
