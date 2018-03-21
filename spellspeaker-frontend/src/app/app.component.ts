import { Component } from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {Router} from "@angular/router";
import {AuthenticationService} from "./service/authentication.service";
import {UserProfile} from "./model/user-profile";
import {UserProfileService} from "./service/user-profile-service";
import {MessageComponent} from "./component/message/message.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'Spellspeaker';

  constructor(
    private authenticationService: AuthenticationService,
    private userProfileService: UserProfileService
  ) {
  }

  getUserProfile(): UserProfile {
    return this.userProfileService.userProfile;
  }

  logout(): void {
    this.authenticationService.logout();
  }
}
