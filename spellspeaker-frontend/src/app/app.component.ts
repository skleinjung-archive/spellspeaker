import { Component } from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {Router} from "@angular/router";
import {AuthenticationService} from "./authentication.service";
import {UserProfile} from "./user-profile";
import {UserProfileService} from "./user-profile-service";
import {MessageComponent} from "./message.component";

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
