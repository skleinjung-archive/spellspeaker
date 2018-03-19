import {Component, Input, OnInit} from '@angular/core';
import {Card} from '../model/card';

@Component({
  selector: 'app-card-set',
  templateUrl: './card-set.component.html',
  styleUrls: ['./card-set.component.css']
})
export class CardSetComponent implements OnInit {

  @Input() cards: Card[];
  @Input() selectionEnabled: boolean;

  constructor() { }

  ngOnInit() {
  }

  selectIfEnabled(selectedCard: Card) {
    if (this.selectionEnabled) {
      for (let i = 0; i < this.cards.length; i++) {
        const card = this.cards[i];
        card.selected = false;
      }
      selectedCard.selected = true;
    }
  }
}
