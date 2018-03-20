import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {Game} from './model/game';
import {GameService} from './service/game.service';
import {GameSummary} from './model/game-summary';

@Component({
  selector: 'games',
  templateUrl: './games.component.html',
  styleUrls: ['./games.component.css']
})
export class GamesComponent implements OnInit {
  constructor(
    private gameService: GameService,
    private router: Router) {
  }

  games: GameSummary[];
  selectedGame: Game;

  ngOnInit(): void {
    this.getGames();
  }

  getGames(): void {
    this.gameService.getGames().subscribe(games => this.games = games);
  }

  onSelect(game: Game): void {
    this.router.navigate(['/games', game.id]);
  }

  createNewGame() {
    this.gameService.createGame().subscribe(response => {
      this.router.navigate(['/games', response.newGameId]);
    });
  }
}

