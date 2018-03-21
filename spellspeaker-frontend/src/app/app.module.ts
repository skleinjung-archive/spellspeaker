import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {HttpModule} from '@angular/http';
import {FormsModule} from '@angular/forms';
import {GamesComponent} from './component/games/games.component';
import {GameService} from './service/game.service';
import {GameDetailComponent} from './component/game-detail/game-detail.component';
import {RulesService} from './service/rules.service';
import {LoginComponent} from './component/login/login.component';
import {AuthenticationService} from './service/authentication.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthenticationInterceptor} from './authentication-interceptor';
import {UserProfileService} from './service/user-profile-service';
import {MessageService} from './service/message-service';
import {MessageComponent} from './component/message/message.component';
import {ErrorInterceptor} from './error-interceptor';
import {PlayerStatsComponent} from './component/player-stats/player-stats.component';
import {CardSetComponent} from './component/card-set/card-set.component';
import {ConfigurationComponent} from './component/configuration/configuration.component';
import {ConfigurationService} from './service/configuration.service';

@NgModule({
  declarations: [
    AppComponent,
    MessageComponent,
    LoginComponent,
    GamesComponent,
    GameDetailComponent,
    PlayerStatsComponent,
    CardSetComponent,
    ConfigurationComponent
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
    ConfigurationService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
