///<reference path="../../node_modules/@angular/core/src/metadata/directives.d.ts"/>
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from "./service/authentication.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(
    private authenticationService: AuthenticationService,
    private router: Router) {
  }

  ngOnInit(): void {
  }

  onLogin(username: string, password: string): void {
    this.authenticationService.loginWithCredentials(username, password,
      (result) => {
        if (result === true) {
          this.router.navigate(['/games']);
        }
    });
  }
}

