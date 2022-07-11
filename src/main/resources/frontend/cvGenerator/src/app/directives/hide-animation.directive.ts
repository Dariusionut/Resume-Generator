import {Directive, Input, OnInit} from '@angular/core';

@Directive({
  selector: '[appHideAnimation]'
})
export class HideAnimationDirective implements OnInit {

  constructor() {
  }

  @Input('appHideAnimation') element?: string;

  table?: any;

  ngOnInit(): void {
    const table = $('#' + this.element);
    console.log('table', table)

  }

}
