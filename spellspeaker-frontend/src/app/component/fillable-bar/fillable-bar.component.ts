import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-fillable-bar',
  templateUrl: './fillable-bar.component.html',
  styleUrls: ['./fillable-bar.component.css']
})
export class FillableBarComponent implements OnInit {

  @Input() type: string;
  @Input() value: number;
  @Input() maxValue: number;

  constructor() { }

  ngOnInit() {
  }

  range(start, end, step, offset): number[] {

    const len = (Math.abs(end - start) + ((offset || 0) * 2)) / (step || 1) + 1;
    const direction = start < end ? 1 : -1;
    const startingPoint = start - (direction * (offset || 0));
    const stepSize = direction * (step || 1);

    return Array(len).fill(0).map(function(_, index) {
      return startingPoint + (stepSize * index);
    });
  }

  getBarCount(): number {
    return Math.ceil(this.maxValue / 5);
  }

  barRange(): number[] {
    return this.range(1, this.getBarCount(), 1, 0);
  }

  getFraction(whichBar: number) {
    const lastColoredBarIndex = Math.ceil(this.value / 5);
    if (whichBar > lastColoredBarIndex) {
      return '0/5';
    } else if (whichBar < lastColoredBarIndex) {
      return '5/5';
    } else {
      const remainder = this.value - (5 * (lastColoredBarIndex - 1));
      return remainder + '/5';
    }
  }
}
