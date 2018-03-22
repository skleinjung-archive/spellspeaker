import {Injectable} from '@angular/core';

import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/finally';

import {UserProfile} from "../model/user-profile";
import {Router} from "@angular/router";

@Injectable()
export class UserProfileService {

  private static AUTHENTICATION_TOKEN_KEY = 'spellspeaker.authenticationToken';
  private static USER_PROFILE_KEY = 'spellspeaker.userProfile';

  constructor(private router: Router) { }

  get authenticationToken(): string {
    return localStorage.getItem(UserProfileService.AUTHENTICATION_TOKEN_KEY);
  }

  set authenticationToken(value: string) {
    if (value == null) {
      localStorage.removeItem(UserProfileService.AUTHENTICATION_TOKEN_KEY);
    } else {
      localStorage.setItem(UserProfileService.AUTHENTICATION_TOKEN_KEY, value);
    }
  }

  get userProfile(): UserProfile {
    const json = localStorage.getItem(UserProfileService.USER_PROFILE_KEY);
    return json == null ? null : new UserProfile().loadFrom(JSON.parse(json));
  }

  set userProfile(value: UserProfile) {
    if (value == null) {
      localStorage.removeItem(UserProfileService.USER_PROFILE_KEY);
    } else {
      localStorage.setItem(UserProfileService.USER_PROFILE_KEY, JSON.stringify(value));
    }
  }

  clearCredentials(): void {
    this.userProfile = null;
    this.authenticationToken = null;
  }
}
