import {NgModule} from '@angular/core';
import {RouterModule, Routes}   from '@angular/router';
import {GamesComponent} from "./games.component";
import {GameDetailComponent} from "./game-detail.component";
import {LoginComponent} from "./login.component";

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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
