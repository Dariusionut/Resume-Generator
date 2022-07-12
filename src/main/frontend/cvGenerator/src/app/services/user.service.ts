import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user";
import {Observable} from "rxjs";
import {Router} from "@angular/router";
import {DataObject} from "../models/util/data-object";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url: string = environment.apiBaseUrl + '/user/';
  headers: any = {'Content-Type': 'application/json'};

  userId?: number;

  constructor(private http: HttpClient, private router: Router) {
  }

  public getAll(params: string): Observable<DataObject<User>> {

    return this.http.get<DataObject<User>>(this.url + 'list?' + params, {
      headers: this.headers,
      responseType: 'json'
    });
  }

  public onReset() {
    return this.http.get<DataObject<User>>(this.url + 'list?pageSize=10', {
      headers: this.headers,
      responseType: 'json'
    });
  }

  findUserById(userId: number): Observable<User> {
    return this.http.get<User>(this.url + 'byId?userId=' + userId, {
      headers: this.headers,
      responseType: 'json'
    });
  }

  setUserId(userId: string): void {
    this.userId = parseInt(userId);

  }

  addOrEdit(user: User, addForm: boolean): Observable<any> {
    const headers = {'Content-Type': 'application/json'};
    if (addForm) {
      return this.http.post(this.url + 'create', JSON.stringify(user), {headers: headers});
    } else {
      return this.http.put(this.url + 'updateUser', JSON.stringify(user), {headers: headers});
    }
  }


  deleteUser(userId?: number) {
    this.http.delete(this.url + 'delete?userId=' + userId, {
      headers: this.headers,
      responseType: "text"
    })
      .subscribe({
        next: (data: string) => {
          console.log(data);
          this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
            this.router.navigate(['/user/list']));
        },
        error: err => console.error(err)
      })
  }

  removeAllInactive(): void {
    this.http.delete(this.url + 'removeAllInactive', {headers: this.headers, responseType: 'text'})
      .subscribe({
        next: (data: string) => {
          console.log(data);
          this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
            this.router.navigate(['/user/list']));
        },
        error: err => {
          console.error(err);
        }
      })
  }

  toggleUserActive(userId: string): Observable<User> {
    return this.http.put<User>(this.url + 'toggle-enabled?userId=' + userId, {}, {
      headers: this.headers,
      responseType: "json"
    });
  }
}
