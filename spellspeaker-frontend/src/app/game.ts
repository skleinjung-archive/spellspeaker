export class Game {
  id: number;
  currentTick: number;

  numberOfCardsInLibrary: number;

  bluePlayer: Player;
  redPlayer: Player;

  market: Card[];
  hand: Card[];
}

export class Player {
  nextTurnTick: number;
  health: number;
  mana: number;
  numberOfCardsInHand: number;
}

export class Card {
  name: string;
  manaCost: number;
  castingTime: number;
}

