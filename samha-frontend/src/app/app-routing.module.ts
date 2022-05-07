import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {AuthGuard} from './guards/auth-guard';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'login'},
  { path: 'login', component: LoginComponent},
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'professor', canActivate: [AuthGuard], loadChildren: () => import('./professor/professor.module').then(m => m.ProfessorModule) },
  { path: 'coordenador', canActivate: [AuthGuard], loadChildren: () => import('./coordenador/coordenador.module').then(m => m.CoordenadorModule)},
  { path: 'usuario', canActivate: [AuthGuard], loadChildren: () => import('./usuario/usuario.module').then(m => m.UsuarioModule)},
  { path: 'curso', canActivate: [AuthGuard], loadChildren: () => import('./curso/curso.module').then(m => m.CursoModule)},
  { path: 'coordenadoria', canActivate: [AuthGuard], loadChildren: () => import('./coordenadoria/coordenadoria.module').then(m => m.CoordenadoriaModule)},
  { path: 'eixo', canActivate: [AuthGuard], loadChildren: () => import('./eixo/eixo.module').then(m => m.EixoModule)},
  { path: 'disciplina', canActivate: [AuthGuard], loadChildren: () => import('./disciplina/disciplina.module').then(m => m.DisciplinaModule)},
  { path: 'matrizCurricular', canActivate: [AuthGuard], loadChildren: () => import('./matriz-curricular/matriz.module').then(m => m.MatrizModule)},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
