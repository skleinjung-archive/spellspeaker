import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {Game} from './game';
import {GameService} from "./game.service";

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

  games: Game[];
  selectedGame: Game;

  ngOnInit(): void {
    this.getGames();
  }

  getGames(): void {
    this.gameService.getGames().subscribe(games => this.games = games);
  }

  onSelect(game: Game): void {
    this.router.navigate(['/games', game.id])
  }
}

