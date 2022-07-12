import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit, OnDestroy {

  message: string = 'Invalid credentials!';

  redirectMsg?: string = localStorage.getItem('redirectMsg') || undefined;

  showMsg: boolean = false;

  constructor(private router: Router) {
    this.router = router;
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    localStorage.removeItem('redirectMsg');
  }

  login(): void {

    this.router.navigateByUrl('/user');

  }


}
