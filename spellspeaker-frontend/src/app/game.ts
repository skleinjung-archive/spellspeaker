export class Game {
  id: number;
  currentTick: number;

  numberOfCardsInLibrary: number;
  blueNextTurnTick: number;
  redNextTurnTick: number;

  market: Card[];
  hand: Card[];
}

export class Card {
  name: string;
  manaCost: number;
  castingTime: number;
}
