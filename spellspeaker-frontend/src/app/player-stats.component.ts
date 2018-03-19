import {Component, Input, OnInit} from '@angular/core';

import {Player} from './model/player';

@Component({
  selector: 'app-player-stats',
  templateUrl: './player-stats.component.html',
  styleUrls: ['./game-detail.shared.css', './player-stats.component.css']
})
export class PlayerStatsComponent implements OnInit {
  @Input() bluePlayer: Player;
  @Input() redPlayer: Player;

  constructor(
  ) {}

  ngOnInit(): void {
  }

  playerHasRitual(player: Player) {
    return player.ritual && player.ritual.length > 0;
  }
}

