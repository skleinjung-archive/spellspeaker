import {Game} from './game';
import {StateChange} from './state-change';

export class ActionResult {
  game: Game;
  stateChanges: StateChange[];
}
