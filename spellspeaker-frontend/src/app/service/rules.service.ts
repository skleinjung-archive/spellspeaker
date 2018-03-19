import {Injectable} from '@angular/core';
import {Headers} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {Rules} from "../model/rules";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class RulesService {
  private gamesUrl = 'api/rules';  // URL to web api

  constructor(private http: HttpClient) { }

  getRules(): Observable<Rules> {
    return this.http.get<Rules>(this.gamesUrl);
      // .toPromise()
      // .then(response => response.json() as Rules)
      // .catch(this.handleError);
  }
}
