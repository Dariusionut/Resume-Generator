import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  foundUser?: User;

  constructor() {
  }

  ngOnInit(): void {

  }

  findUserById(userId:number){

  }
}
