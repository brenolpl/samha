import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {FormResolver} from '../guards/form-resolver';
import {DisciplinaFormComponent} from './disciplina-form/disciplina-form.component';
import {LogComponent} from '../shared/log/log.component';
import {DisciplinaComponent} from './disciplina/disciplina.component';

export const cursoRoutes: Routes = [
  {path: '', children: [
      {path: '', component: DisciplinaComponent},
      {path: 'new', component: DisciplinaFormComponent},
      {path: 'log', component: LogComponent},
      {path: ':entity', component: DisciplinaFormComponent, resolve: {disciplina: FormResolver}}
    ]}
]

@NgModule({
  imports: [RouterModule.forChild(cursoRoutes)],
  exports: [RouterModule]
})
export class DisciplinaRoutingModule {}
