import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {CardsConfigurationResponse} from '../model/cards-configuration-response';
import {MessageService} from './message-service';

@Injectable()
export class ConfigurationService {
  private configurationUrl = 'api/configuration';  // URL to web api
  private headers = new HttpHeaders({'Content-Type': 'application/json; charset=utf-8'});

  constructor(private http: HttpClient,
              private messageService: MessageService) { }

  getCardsJson(): Observable<CardsConfigurationResponse> {
    return this.http.get<CardsConfigurationResponse>(this.configurationUrl + '/cards');
  }

  updateCardsJson(newJson: string): void {
      this.http.post(this.configurationUrl + '/cards',
        {encodedCardsJson: btoa(newJson)},
        {headers: this.headers})
        .subscribe(response => {
          this.messageService.showSuccess('Cards JSON updated successfully.');
        });
  }

}
