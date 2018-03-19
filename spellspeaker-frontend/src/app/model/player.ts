import {Card} from './card';

export class Player {
  nextTurnTick: number;
  health: number;
  mana: number;
  numberOfCardsInHand: number;
  activeCard: Card;
  ritual: Card[];
}
