import {Component, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html',
  styleUrls: ['./not-found.component.scss']
})
export class NotFoundComponent implements OnInit {

  backRoute?: string;

  constructor() {
    console.log(history)
  }

  ngOnInit(): void {
  }

  onTheClick(){

  }

}
