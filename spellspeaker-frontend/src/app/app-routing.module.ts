import {NgModule} from '@angular/core';
import {RouterModule, Routes}   from '@angular/router';
import {GamesComponent} from './component/games/games.component';
import {GameDetailComponent} from './component/game-detail/game-detail.component';
import {LoginComponent} from './component/login/login.component';
import {ConfigurationComponent} from './component/configuration/configuration.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'games',
    component: GamesComponent
  },
  {
    path: 'games/:id',
    component: GameDetailComponent
  },
  {
    path: 'configuration',
    component: ConfigurationComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
