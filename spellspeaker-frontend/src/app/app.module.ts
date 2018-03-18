import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import {HttpModule} from "@angular/http";
import {FormsModule} from "@angular/forms";
import {GamesComponent} from "./games.component";
import {GameService} from "./game.service";
import {GameDetailComponent} from "./game-detail.component";
import {RulesService} from "./rules.service";
import {LoginComponent} from "./login.component";
import {AuthenticationService} from "./authentication.service";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthenticationInterceptor} from "./authentication-interceptor";
import {UserProfileService} from "./user-profile-service";
import {MessageService} from "./message-service";
import {MessageComponent} from "./message.component";

@NgModule({
  declarations: [
    AppComponent,
    MessageComponent,
    GamesComponent,
    LoginComponent,
    GameDetailComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [
    MessageService,
    GameService,
    RulesService,
    AuthenticationService,
    UserProfileService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
