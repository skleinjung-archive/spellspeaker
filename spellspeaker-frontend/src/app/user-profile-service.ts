import {Injectable} from '@angular/core';

import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/finally';

import {UserProfile} from "./user-profile";
import {Router} from "@angular/router";

@Injectable()
export class UserProfileService {
  private _authenticationToken: String;
  private _userProfile: UserProfile;

  constructor(private router: Router) { }

  get authenticationToken(): String {
    return this._authenticationToken;
  }

  set authenticationToken(value: String) {
    this._authenticationToken = value;
  }

  get userProfile(): UserProfile {
    return this._userProfile;
  }

  set userProfile(value: UserProfile) {
    this._userProfile = value;
  }

  clearCredentials(): void {
    this.userProfile = null;
    this.authenticationToken = null;
  }
}
