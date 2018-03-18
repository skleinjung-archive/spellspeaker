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
}

export class Player {
  nextTurnTick: number;
  health: number;
  mana: number;
  numberOfCardsInHand: number;
  ritual: Card[];
}

export class Card {
  name: string;
  manaCost: number;
  castingTime: number;
}

