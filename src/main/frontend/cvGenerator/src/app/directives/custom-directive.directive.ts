import {Directive, ElementRef, HostBinding, HostListener, Input, OnInit, Renderer2} from '@angular/core';

@Directive({
  selector: '[custom-directive]'
})
export class CustomDirectiveDirective implements OnInit {

  constructor(private el: ElementRef, private renderer: Renderer2) {
  }

  @Input('default-color') defaultColor?: string;
  @Input('custom-directive') highLightColor: string = 'brown';
  @Input() userId?: string;

  @HostBinding('style.backgroundColor') bgColor?: string = this.defaultColor;

  @HostListener('mouseenter') mouseOver(eventData: Event) {
    this.bgColor = this.highLightColor;


  }

  @HostListener('mouseleave') mouseLeave(eventData: Event) {
    this.bgColor = this.defaultColor;
  }

  @HostListener('click') focus(eventData: Event) {
    alert(this.userId);
  }


  ngOnInit(): void {
  }
}
