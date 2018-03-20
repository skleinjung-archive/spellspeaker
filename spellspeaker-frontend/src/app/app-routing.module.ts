import {NgModule} from '@angular/core';
import {RouterModule, Routes}   from '@angular/router';
import {GamesComponent} from './games.component';
import {GameDetailComponent} from './game-detail.component';
import {LoginComponent} from './login.component';
import {ConfigurationComponent} from './configuration/configuration.component';

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
