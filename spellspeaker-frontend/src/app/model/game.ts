import {Player} from './player';
import {Card} from './card';
import {InputRequest} from './input-request';

export class Game {
  id: number;
  numberOfCardsInLibrary: number;
  currentTick: number;

  bluePlayer: Player;
  redPlayer: Player;
  activePlayerColor: string;
  currentUserColor: string;

  inputRequest: InputRequest;

  market: Card[];
  hand: Card[];

  type: string;
}

