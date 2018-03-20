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

@Injectable()
export class GameService {
  private gamesUrl = 'api/games';  // URL to web api
  private headers = new HttpHeaders({'Content-Type': 'application/json; charset=utf-8'});

  constructor(
    private http: HttpClient) { }

  getGames(): Observable<Game[]> {
    return this.http.get<Game[]>(this.gamesUrl);
  }

  getGame(id: number): Observable<Game> {
    const url = `${this.gamesUrl}/${id}`;
    return this.http.get<Game>(url);
  }

  playCardFromHand(gameId: number, card: Card): Observable<ActionResult> {
    const url = this.gamesUrl + '/' + gameId + '/actions';
    return this.http.post<ActionResult>(url, JSON.stringify({
      action: 'PlayCardFromHand',
      card: card.name
    }), {headers: this.headers});
      // .map(result => {
      //   const newResult = new ActionResult();
      //   newResult.game = result.game;
      //   newResult.stateChanges = [];
      //
      //   for (let i = 0; i < result.stateChanges.length; i++) {
      //     let stateChange;
      //     switch (result.stateChanges[i].type) {
      //       case 'AddedToRitual':
      //         stateChange = new AddedToRitualStateChange();
      //         break;
      //
      //       default:
      //         stateChange = new StateChange();
      //     }
      //
      //     for (const p in result.stateChanges[i]) {
      //       stateChange[p] = result.stateChanges[i][p];
      //     }
      //
      //     newResult.stateChanges.push(stateChange);
      //   }
      //
      //   return newResult;
      // });
  }

  discardCardFromHand(gameId: number, card: Card): Observable<ActionResult> {
    const url = this.gamesUrl + '/' + gameId + '/actions';
    return this.http.post<ActionResult>(url, JSON.stringify({
      action: 'DiscardCardFromHand',
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

  // create(name: string): Promise<Game> {
  //   return this.http
  //     .post(this.gamesUrl, JSON.stringify({name: name}), {headers: this.headers})
  //     .toPromise()
  //     .then(response => response.json().data as Game)
  //     .catch(this.handleError);
  // }
}
