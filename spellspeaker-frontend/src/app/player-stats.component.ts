import {Component, Input, OnInit} from '@angular/core';

import {Player} from './model/player';
import {Router} from "@angular/router";
import {AuthenticationService} from "./service/authentication.service";

@Component({
  selector: 'app-player-stats',
  templateUrl: './player-stats.component.html',
  styleUrls: ['./game-detail.shared.css', './player-stats.component.css']
})
export class PlayerStatsComponent implements OnInit {
  @Input() bluePlayer: Player;
  @Input() redPlayer: Player;
  @Input() blueRitualUpdates: string[];
  @Input() redRitualUpdates: string[];
  @Input() activePlayerColor: string;

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router) {
  }

  ngOnInit(): void {
  }

  playerHasRitual(player: Player) {
    return player.ritual && player.ritual.cards && player.ritual.cards.length > 0;
  }

  isRitualCardUpdated(player: Player, cardName: string): boolean {
    return true;

    // if (player === this.bluePlayer) {
    //   return this.blueRitualUpdates.indexOf(cardName) !== -1;
    // } else {
    //   return this.redRitualUpdates.indexOf(cardName) !== -1;
    // }
  }

  switchToUser(username: string) {
    this.authenticationService.loginWithCredentials(username, 'password',
      (result) => {
        if (result === true) {
          this.router.navigate(['/games']).then(() =>{
            this.router.navigate(['/games/101']);
          });
        }
      });
  }
}

