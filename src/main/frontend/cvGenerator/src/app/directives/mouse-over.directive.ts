import {Directive, ElementRef, HostBinding, HostListener, Input, Renderer2} from '@angular/core';

@Directive({
  selector: '[appMouseOver]'
})
export class MouseOverDirective {
  constructor(private el: ElementRef, private renderer: Renderer2) {
  }

  @Input('AppMouseOver') appMouseOver?: string = this.el.nativeElement.style.opacity;

  @HostBinding('style.opacity') opacity?: string = this.appMouseOver;

  @HostListener('mouseenter') mouseEnter(eventData: Event) {
    this.opacity = '90%';
  }

  @HostListener('mouseleave') mouseLeave(eventData: Event) {
    this.opacity = this.appMouseOver;
  }

  @HostListener('mousedown') mouseDown() {
    this.opacity = '80%';
  }

  @HostListener('mouseup') mouseUp() {
    this.opacity = '90%';
  }


}
