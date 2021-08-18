import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutMeComponent } from './about-me/about-me.component';
import { HomeComponent } from './home/home.component';
import { TtaalComponent } from './ttaal/ttaal.component';

const routes: Routes = [{
  path:'home',
  component: HomeComponent
}, {
  path:'about-me',
  component: AboutMeComponent
}, {
  path: 'ttaal',
  component: TtaalComponent
}, {
  path:'',
  component: HomeComponent
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
