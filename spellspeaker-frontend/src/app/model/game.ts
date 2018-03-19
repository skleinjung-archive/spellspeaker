import {Player} from './player';
import {Card} from './card';

export class Game {
  id: number;
  numberOfCardsInLibrary: number;
  currentTick: number;

  bluePlayer: Player;
  redPlayer: Player;
  activePlayerColor: string;
  currentUserColor: string;

  expectedInput: string;

  market: Card[];
  hand: Card[];

  type: string;

  func(): void {
  }

  getMessage(): string {
    console.debug(this);
    switch (this.type) {
      case 'AddedToRitual':
        return this['card'] + ' added to ' + this['player'] + '\'s ritual.';

      default:
        return '';
    }
  }
}

