import {Injectable} from '@angular/core';

import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/finally';

import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import {UserProfile} from './user-profile';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {UserProfileService} from './user-profile-service';

@Injectable()
export class AuthenticationService {
  private profileUrl = 'api/user/profile';
  private loginUrl = 'api/auth/login';
  private logoutUrl = 'api/auth/logout';

  constructor(
    private http: HttpClient,
    private router: Router,
    private userProfileService: UserProfileService
  ) { }

  // stub implementation at current time, until login functionality is implemented
  loginWithCredentials(username: string, password: string, callback): void {
    const credentialHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'X-TT-Authentication-Username': username,
      'X-TT-Authentication-Password': password,
    });

    this.http.post(
      this.loginUrl, JSON.stringify({}), {headers: credentialHeaders})
      .subscribe(loginResponse => {
        const authenticationTokenData = loginResponse['authenticationToken'];
        let authenticationToken = null;
        if (authenticationTokenData['token']) {
          authenticationToken = authenticationTokenData['token'];
        }

        const userProfileData = loginResponse['userProfile'];
        if (authenticationToken && userProfileData) {
          const userProfile = new UserProfile();
          userProfile.username = userProfileData['username'];

          this.userProfileService.userProfile = userProfile;
          this.userProfileService.authenticationToken = authenticationToken;

          return callback && callback(true);
        } else {
          this.userProfileService.userProfile = null;
          return callback && callback(false);
        }
      });
  }

  loginWithToken(authenticationToken: string): Observable<boolean> {
    return null;
  }

  logout(): void {
    this.http.post(this.logoutUrl, {}).finally(() => {
      this.userProfileService.clearCredentials();
      this.router.navigateByUrl('/login');
    }).subscribe();
  }
}
