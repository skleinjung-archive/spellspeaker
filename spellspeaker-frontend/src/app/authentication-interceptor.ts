import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest, HttpErrorResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import {Injectable} from '@angular/core';
import {UserProfileService} from './service/user-profile-service';
import {Router} from '@angular/router';

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {
  constructor(
    private userProfileService: UserProfileService,
    private router: Router,
    private userService: UserProfileService
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // needed to suppress browse-based basic auth, triggered by Spring Security headers
    let clonedRequest = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });

    clonedRequest = req.clone({
      headers: req.headers.set('Content-Type', 'application/json')
    });

    const authenticationToken = this.userProfileService.authenticationToken as string;
    if (authenticationToken && authenticationToken !== null) {
      clonedRequest = req.clone({
        headers: req.headers.set('X-TT-Authentication-Token', authenticationToken)
      });
    }

    // Pass the cloned request instead of the original request to the next handle
    return next.handle(clonedRequest).catch(error => {
      if (error instanceof HttpErrorResponse) {
        switch ((<HttpErrorResponse>error).status) {
          case 401:
            return this.handle401Error();
        }
      }

      return Observable.throw(error);
    });
  }

  handle401Error() {
    this.userProfileService.clearCredentials();
    this.router.navigateByUrl('/login');
    return Observable.throw('User logged out.');
  }
}
