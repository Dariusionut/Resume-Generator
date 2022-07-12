import {AfterViewInit, Component, ElementRef, EventEmitter, OnInit, Output} from '@angular/core';
import {ActivatedRoute, NavigationEnd, NavigationStart, Router} from "@angular/router";
import {filter} from "rxjs/operators";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit {
  showHeader: boolean = true;

  darkMode?: boolean = JSON.parse(window.localStorage.getItem('isDarkMode') || 'false');

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private elementRef: ElementRef) {
    router.events.forEach(e => {
      if (e instanceof NavigationStart) {
        this.showHeader = !(e['url'] === '/login' || e['url'] === '/' || e['url'] === '/new-account');
      }
    });

    this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationStart) {
        this.showHeader = !(e.url === '/login' || e.url === '/' || e['url'] === '/new-account');
      }
    });
  }

  toggleDarkMode(event: any) {
    this.darkMode = event;
    this.toggleBodyStyle();
  }

  ngAfterViewInit(): void {
    this.toggleBodyStyle();
  }

  toggleBodyStyle(): void {
    this.elementRef.nativeElement.ownerDocument
      .body.style.backgroundColor = this.darkMode ? 'rgba(0, 0, 0, 0.8)' : 'white'
  }

  ngOnInit(): void {
  }


}
