import {Component, Input, OnInit} from '@angular/core';

import {Player} from '../../model/player';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../service/authentication.service';
import {Rules} from "../../model/rules";

@Component({
  selector: 'app-player-stats',
  templateUrl: './player-stats.component.html',
  styleUrls: ['../game-detail/game-detail.shared.css', './player-stats.component.css']
})
export class PlayerStatsComponent implements OnInit {
  @Input() gameId: number;
  @Input() player: Player;
  @Input() ritualUpdates: string[];
  @Input() color: string;
  @Input() activePlayer: boolean;
  @Input() rules: Rules;

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router) {
  }

  ngOnInit(): void {
  }

  getAfflictionTrack(afflictionStrength: number): boolean[] {
    const result = [];
    for (let i = 0; i < this.rules.maximumAfflictions; i++) {
      if (afflictionStrength > i) {
        result.push(true);
      } else {
        result.push(false);
      }
    }
    return result;
  }

  playerHasRitual(player: Player) {
    return player.ritual && player.ritual.cards && player.ritual.cards.length > 0;
  }

  isRitualCardUpdated(player: Player, cardName: string): boolean {
    return true;

    // if (player === this.player) {
    //   return this.ritualUpdates.indexOf(cardName) !== -1;
    // } else {
    //   return this.redRitualUpdates.indexOf(cardName) !== -1;
    // }
  }
}

