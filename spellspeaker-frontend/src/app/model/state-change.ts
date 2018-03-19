export class StateChange {
  type: string;
  message: string;
}

export class AddedToRitualStateChange extends StateChange {
  player: string;
  card: string;
}

export class BeganCastingStateChange extends StateChange {
  player: string;
  card: string;
}
