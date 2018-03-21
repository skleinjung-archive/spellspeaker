import {Component, Injectable, Input, OnInit} from '@angular/core';
import {Card} from '../../model/card';

@Component({
  selector: 'app-card-set',
  templateUrl: './card-set.component.html',
  styleUrls: ['./card-set.component.css']
})
export class CardSetComponent implements OnInit {

  @Input() cards: Card[];
  @Input() updatedCardNames: string[];
  @Input() selectionEnabled: boolean;

  constructor() { }

  ngOnInit() {
  }

  isCardUpdated(cardName: string): boolean {
    return this.updatedCardNames && this.updatedCardNames.indexOf(cardName) !== -1;
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
