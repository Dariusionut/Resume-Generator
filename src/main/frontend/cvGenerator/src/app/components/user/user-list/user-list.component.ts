import {
  AfterViewInit,
  Component, OnDestroy,
  OnInit,

} from '@angular/core';
import {trigger, state, style, animate, transition} from "@angular/animations";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../services/user.service";
import {DataObject} from "../../../models/util/data-object";
import {HttpErrorResponse} from "@angular/common/http";
import {UserFilter} from "../../../models/util/user-filter";
import {User} from "../../../models/user";
import {Observable} from "rxjs";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
  animations: [

    trigger('showTable', [
      state('open', style({
        opacity: 10,
      })),
      state('closed', style({
        opacity: 0,
        transform: 'scale(-70%)',

      })),
      transition('open => closed', [
        animate('0.5s')
      ]),
      transition('closed => open', [
        animate('0.5s')
      ]),
    ]),
  ]
})

export class UserListComponent implements OnInit, AfterViewInit, OnDestroy {

  redirectMsg?: string = localStorage.getItem('redirectMsg') || undefined;

  showTable: boolean = JSON.parse(localStorage.getItem('showTable') || 'true');
  showTableActions: boolean = JSON.parse(window.localStorage.getItem('showUserTableActions') || 'true');

  constructor(private router: Router, private activeRoute: ActivatedRoute, private userService: UserService) {
    this.router = router;
    this.userService = userService;
  }

  ngOnDestroy(): void {
    localStorage.removeItem('redirectMsg');
  }

  orderColumn?: string = localStorage.getItem('orderColumn') || 'username';
  orderAsc?: boolean = JSON.parse(localStorage.getItem('orderAsc') || 'true');

  showFilters: boolean = false;

  userObj?: DataObject<User>;
  params?: UserFilter;
  pages?: number[] = [];


  ngOnInit(): void {
    this.getUsers();

    if (localStorage.getItem('userParams') !== undefined) {
      this.params = JSON.parse(localStorage.getItem('userParams')!);
    }

    const previousUrl = history.state.prevPage ?? null;
    if (!previousUrl) {
      localStorage.removeItem('redirectMsg');
    }
  }

  ngAfterViewInit(): void {


  }

  public resetFilters(userObs: Observable<DataObject<User>>) {
    userObs.subscribe({
      next: (userObj: DataObject<User>) => {
        localStorage.removeItem('userParams')
        this.userObj = userObj;
      },
      error: (err: HttpErrorResponse) => console.error('Cannot reset filters!', err)
    })

  }

  public toggleUserEnabled(userId: string): void {
    this.userService.toggleUserActive(userId)
      .subscribe({
        next: (user: User) => {
          this.userObj?.list.map((u: User) => {
            if (u.id === user.id) {
              u.userSecurity.isEnabled = user.userSecurity.isEnabled;
            }
          });
        },
        error: (err: HttpErrorResponse) => {
          console.error('Cannot toggle user isActive', err.status, err);
        }
      });

  }

  public deleteUser(userId: string): void {
    const user: User = this.userObj?.list.filter(u => u.id === parseInt(userId))[0]!;

    if (confirm(`Are you sure to delete ${user.userDetails.lastName} ${user.userDetails.firstName}?`)) {
      this.userService.deleteUser(parseInt(userId));
    }

  }

  public getUsers(): void {
    this.userService.getAll($.param(JSON.parse(localStorage.getItem('userParams')!))).subscribe({
      next: (userData: DataObject<User>) => {
        this.userObj = userData;

      },
      error: (err: HttpErrorResponse) => console.error('Something went wrong! ' + err)
    });

  }

  public filterUsers(event: any) {
    console.log(event)
    this.params = event
    let filters = this.params;

    if (this.orderColumn !== undefined) filters!.order = this.orderColumn;
    if (this.orderAsc !== undefined) filters!.isAsc = this.orderAsc;

    this.userService.getAll(this.params ? $.param(this.params) : '')
      .subscribe({
        next: (userObj: DataObject<User>) => {
          this.userObj = userObj;
          localStorage.setItem('userParams', JSON.stringify(this.params));
        },
        error: (err: HttpErrorResponse) => {
          console.error('Cannot filter users!', err);
        }
      })
  }

  public toggleFilters(): void {
    this.showFilters = !this.showFilters;
  }

  public changePageRecords(records: { page?: number, pageSize?: string, order?: string, isAsc?: boolean }) {
    if (this.orderColumn === records.order) {
      this.orderAsc = !this.orderAsc;
    } else {
      this.orderAsc = true;
    }
    records.isAsc = this.orderAsc;

    if (records.pageSize) {
      this.userObj!.recordsPerPage = parseInt(records.pageSize);
    } else {
      records.pageSize = this.userObj!.recordsPerPage.toString();
    }
    if (records.order) {
      this.orderColumn = records.order
    } else {
      records.order = this.orderColumn;
    }

    if (this.params !== undefined && this.params !== null) {
      records = Object.assign(this.params, records);
    }
    this.userService.getAll($.param(records))
      .subscribe({
        next: (userObj: DataObject<User>) => {
          this.userObj = userObj;
          records.page = userObj.currentPage;
          localStorage.setItem('userParams', JSON.stringify(records));
        },
        error: (err: HttpErrorResponse) => {
          console.error('Cannot change page records!', err);
        }
      });

  }

  public toggleTable(): void {
    this.showTable = !this.showTable;
    localStorage.setItem('showTable', JSON.stringify(this.showTable));
  }

  public toggleTableActions(): void {
    this.showTableActions = !this.showTableActions;
    window.localStorage.setItem('showUserTableActions', JSON.stringify(this.showTableActions));
  }

  public viewEditForm(userId: string): void {

    this.router.navigate(['/user/user-edit/' + userId])
      .then(() => {
        this.userService.setUserId(userId);

      });
  }

}
