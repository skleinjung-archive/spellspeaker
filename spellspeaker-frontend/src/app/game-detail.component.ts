import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Location} from '@angular/common';

import 'rxjs/add/operator/switchMap';

import {Game} from "./game";
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

  @Input() game: Game;
  rules: Rules;

  ngOnInit(): void {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.gameService.getGame(+params.get('id')))
      .subscribe(game => {
        this.game = game;

        if (game.currentUserColor == null) {
          this.messageService.showInformation('It is ' + game.activePlayerColor + '\'s turn.');
        } else if (game.currentUserColor === game.activePlayerColor) {
          this.messageService.showInformation('It is your turn.');
        } else {
          this.messageService.showInformation('It is your opponent\'s turn.');
        }
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

  goBack(): void {
    this.location.back();
  }

  // save(): void {
  //   this.creatureTypeService.update(this.creatureType)
  //     .then(() => this.goBack());
  // }
}

