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
import {MessageService} from './service/message-service';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(
    private router: Router,
    private messageService: MessageService
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).catch(error => {
      if (error instanceof HttpErrorResponse) {
        switch ((<HttpErrorResponse>error).status) {
          case 400:
            this.handle400Error(error);
            break;

          case 500:
            this.handle500Error(error);
            break;
        }
      }

      return Observable.throw(error);
    });
  }

  handle400Error(errorResponse) {
    let message = '';
    const messages = errorResponse.error.messages[0];
    for (let i = 0; i < messages.length; i++) {
      message += '<div>' + messages[i] + '</div>';
    }

    if ('' === message) {
      message = 'An error occurred processing your request.';
    }

    if (message !== '') {
      this.messageService.showInformation(message);
    }

    this.messageService.showError(errorResponse.error.messages[0]);
  }

  handle500Error(errorResponse) {
    this.messageService.showError(errorResponse.error.message);
  }
}
