import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {

  currentRoute: string = '';
  links: Array<{ route: string, text: string, active?: boolean }> = [{route: '/user', text: 'Users'}];

  darkMode: boolean = JSON.parse(window.localStorage.getItem('isDarkMode') || 'false');

  @Output('isDarkMode')
  isDarkMode = new EventEmitter<boolean>();

  constructor() {
  }

  ngOnInit(): void {
    if (localStorage.getItem('activeLink') === null) {
      this.currentRoute = '/user'
    } else {
      this.currentRoute = localStorage.getItem('activeLink')!;
    }

  }

  ngOnDestroy(): void {
    localStorage.removeItem('activeRoute');
  }

  onLinkClick(route: string): void {

    localStorage.setItem('activeLink', route);
  }

  toggleDarkMode(): void {
    this.darkMode = !this.darkMode;
    localStorage.setItem('isDarkMode', JSON.stringify(this.darkMode));
    this.isDarkMode.emit(this.darkMode);
  }


}
