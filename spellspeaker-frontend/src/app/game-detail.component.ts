import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Location} from '@angular/common';

import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/finally';

import {Game} from './model/game';
import {GameService} from './service/game.service';
import {Rules} from './model/rules';
import {RulesService} from './service/rules.service';
import {MessageService} from './service/message-service';
import {Card} from "./model/card";

@Component({
  selector: 'game-detail',
  templateUrl: './game-detail.component.html',
  styleUrls: ['./game-detail.shared.css', './game-detail.component.css']
})
export class GameDetailComponent implements OnInit {
  constructor(private messageService: MessageService,
              private gameService: GameService,
              private rulesService: RulesService,
              private route: ActivatedRoute,
              private location: Location
  ) {}

  @Input() private _game: Game;
  rules: Rules;

  ngOnInit(): void {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.gameService.getGame(+params.get('id')))
      .subscribe(game => {
        this.game = game;
      });

    this.rulesService.getRules().subscribe(rules => this.rules = rules);
  }

  // https://stackoverflow.com/questions/3895478/does-javascript-have-a-method-like-range-to-generate-a-range-within-the-supp/31989462#31989462
  range(start, end, step, offset): number[] {

    const len = (Math.abs(end - start) + ((offset || 0) * 2)) / (step || 1) + 1;
    const direction = start < end ? 1 : -1;
    const startingPoint = start - (direction * (offset || 0));
    const stepSize = direction * (step || 1);

    return Array(len).fill(0).map(function(_, index) {
      return startingPoint + (stepSize * index);
    });
  }

  get game(): Game {
    return this._game;
  }

  set game(value: Game) {
    this._game = value;
    if (this._game.currentUserColor == null) {
      this.messageService.showInformation('It is ' + this._game.activePlayerColor + '\'s turn.');
    } else if (this._game.currentUserColor === this._game.activePlayerColor) {
      this.messageService.showInformation('It is your turn.');
    } else {
      this.messageService.showInformation('It is your opponent\'s turn.');
    }
  }

  isMarketSelectionEnabled(): boolean {
    // todo: implement this
    return false;
  }

  isHandSelectionEnabled(): boolean {
    return this._game.expectedInput === 'PlayCardFromHand';
  }

  getSelectedCardFromMarket(): Card {
    for (let i = 0; i < this.game.market.length; i++) {
      const card = this.game.market[i];
      if (card.selected) {
        return card;
      }
    }
    return null;
  }

  getSelectedCardFromHand(): Card {
    for (let i = 0; i < this.game.hand.length; i++) {
      const card = this.game.hand[i];
      if (card.selected) {
        return card;
      }
    }
    return null;
  }

  confirmHandSelection(): void {
    this.gameService.playCardFromHand(this._game.id, this.getSelectedCardFromHand())
      .finally(() => {
        window.scrollTo(0, 0);
      })
      .subscribe(game => {
        this.game = game;
      });
  }

  // save(): void {
  //   this.creatureTypeService.update(this.creatureType)
  //     .then(() => this.goBack());
  // }
}

