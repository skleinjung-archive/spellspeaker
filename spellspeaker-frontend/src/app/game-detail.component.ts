import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Location} from '@angular/common';

import 'rxjs/add/operator/switchMap';

import {Card, Game} from "./game";
import {GameService} from "./game.service";
import {Rules} from "./rules";
import {RulesService} from "./rules.service"
import {MessageService} from "./message-service";

@Component({
  selector: 'game-detail',
  templateUrl: './game-detail.component.html',
  styleUrls: ['./game-detail.component.css']
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
  private _selectedCardFromMarket: Card;
  private _selectedCardFromHand: Card;

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
    return this._game.expectedInput === 'SelectCardFromHand';
  }

  get selectedCardFromMarket(): Card {
    return this._selectedCardFromMarket;
  }

  set selectedCardFromMarket(value: Card) {
    if (!this.isMarketSelectionEnabled()) {
      return;
    }

    this._selectedCardFromHand = null;
    this._selectedCardFromMarket = value;
  }

  get selectedCardFromHand(): Card {
    return this._selectedCardFromHand;
  }

  set selectedCardFromHand(value: Card) {
    if (!this.isHandSelectionEnabled()) {
      return;
    }

    this._selectedCardFromMarket = null;
    this._selectedCardFromHand = value;
  }

  confirmHandSelection(): void {
    this.gameService.selectFromHand(this._game.id, this.selectedCardFromHand)
      .subscribe(game => {
        this.game = game;
      });
  }

  goBack(): void {
    this.location.back();
  }

  // save(): void {
  //   this.creatureTypeService.update(this.creatureType)
  //     .then(() => this.goBack());
  // }
}

