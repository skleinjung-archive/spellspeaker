import {Injectable} from '@angular/core';
import {Headers} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {Game} from './game';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class GameService {
  private gamesUrl = 'api/games';  // URL to web api

  constructor(
    private http: HttpClient) { }

  getGames(): Observable<Game[]> {
    return this.http.get<Game[]>(this.gamesUrl);
  }

  getGame(id: number): Observable<Game> {
    const url = `${this.gamesUrl}/${id}`;
    return this.http.get<Game>(url);
      // .toPromise()
      // .then(response => response.json() as Game)
      // .catch(this.handleError);
  }

  // create(name: string): Promise<Game> {
  //   return this.http
  //     .post(this.gamesUrl, JSON.stringify({name: name}), {headers: this.headers})
  //     .toPromise()
  //     .then(response => response.json().data as Game)
  //     .catch(this.handleError);
  // }
}
