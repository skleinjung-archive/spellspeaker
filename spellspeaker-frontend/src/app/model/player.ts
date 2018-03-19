import {Card} from './card';
import {Ritual} from './ritual';

export class Player {
  nextTurnTick: number;
  health: number;
  mana: number;
  numberOfCardsInHand: number;
  activeCard: Card;
  ritual: Ritual;
}
