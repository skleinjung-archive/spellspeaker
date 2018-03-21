import {Component, Input, OnInit} from '@angular/core';
import {ConfigurationService} from '../../service/configuration.service';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.css']
})
export class ConfigurationComponent implements OnInit {

  cardsJson: string;

  constructor(private configurationService: ConfigurationService
  ) {}

  ngOnInit(): void {
    this.configurationService.getCardsJson().subscribe(response => {
      this.cardsJson = atob(response.encodedCardsJson);
    });
  }

  saveCardsJson() {
    this.configurationService.updateCardsJson(this.cardsJson);
  }
}
