import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DataObject} from "../../models/util/data-object";
import {User} from "../../models/user";

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent implements OnInit {

  @Input()
  data_object?: DataObject<User>;

  @Output()
  pageEvent = new EventEmitter<{page?: number, pageSize?: string}>();

  pageRecords: number[] = [1, 2, 3, 5, 10, 25, 50];


  constructor() {
  }

  ngOnInit(): void {
  }

  public changePageRecords(data: {page?: number, pageSize?: string}) {
    this.pageEvent.emit(data);
  }

  public secureClick() {
    const messages: string[] = [
      '403 - Forbidden!',
      "You're not allowed!",
      "Please, don't try this again!",
      "Are you willing to stop? It won't work anyway!"
    ];
    const randomMsgIndex: number = Math.floor(Math.random() * messages.length);
    alert(messages[randomMsgIndex]);
  }

}
