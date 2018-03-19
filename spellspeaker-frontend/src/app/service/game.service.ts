import {Injectable} from '@angular/core';
import {Headers} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {Game} from '../model/game';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Card} from "../model/card";

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

  playCardFromHand(gameId: number, card: Card): Observable<Game> {
    const url = this.gamesUrl + '/' + gameId + '/actions';
    return this.http.post<Game>(url, JSON.stringify({
      action: 'PlayCardFromHand',
      card: card.name
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