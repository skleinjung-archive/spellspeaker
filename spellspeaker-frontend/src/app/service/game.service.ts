import {Injectable} from '@angular/core';
import {Headers} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';

import {Game} from '../model/game';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Card} from "../model/card";
import {ActionResult} from "../model/action-result";
import {AddedToRitualStateChange, StateChange} from "../model/state-change";
import {GameSummary} from "../model/game-summary";
import {CreateGameResponse} from "../model/create-game-response";

@Injectable()
export class GameService {
  private gamesUrl = 'api/games';  // URL to web api
  private headers = new HttpHeaders({'Content-Type': 'application/json; charset=utf-8'});

  constructor(
    private http: HttpClient) { }

  getGames(): Observable<GameSummary[]> {
    return this.http.get<GameSummary[]>(this.gamesUrl);
  }

  createGame(): Observable<CreateGameResponse> {
    return this.http.post<CreateGameResponse>(this.gamesUrl, {}, {headers: this.headers});
  }

  getGame(id: number): Observable<Game> {
    const url = `${this.gamesUrl}/${id}`;
    return this.http.get<Game>(url);
  }

  selectCardFromHand(gameId: number, card: Card): Observable<ActionResult> {
    return this.selectCard('SelectCardFromHand', gameId, card);
  }

  selectCardFromMarket(gameId: number, card: Card): Observable<ActionResult> {
    return this.selectCard('SelectCardFromMarket', gameId, card);
  }

  selectCard(action: string, gameId: number, card: Card) {
    const url = this.gamesUrl + '/' + gameId + '/actions';
    return this.http.post<ActionResult>(url, JSON.stringify({
      action: action,
      card: card.name
    }), {headers: this.headers});
  }

  submitUserInput(gameId: number, userInput: string): Observable<ActionResult> {
    const url = this.gamesUrl + '/' + gameId + '/actions';
    return this.http.post<ActionResult>(url, JSON.stringify({
      action: 'SubmitUserInput',
      userInput: userInput
    }), {headers: this.headers});
  }

  completeRitual(gameId: number) {
    const url = this.gamesUrl + '/' + gameId + '/actions';
    return this.http.post<ActionResult>(url, JSON.stringify({
      action: 'CompleteRitual'
    }), {headers: this.headers});
  }

  // create(name: string): Promise<Game> {
  //   return this.http
  //     .post(this.gamesUrl, JSON.stringify({name: name}), {headers: this.headers})
  //     .toPromise()
  //     .then(response => response.json().data as Game)
  //     .catch(this.handleError);
  // }

}
