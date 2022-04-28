import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TableComponent} from '../shared/table/table.component';
import {ProfessorFormComponent} from './professor-form/professor-form.component';
import {AuthGuard} from '../guards/auth-guard';

export const professorRoutes: Routes = [
  //TODO: Criar uma forma de injetar a entity no table component
  { path: '', children: [
      {path: '', component: TableComponent},
      {path: '/new', component: ProfessorFormComponent}
    ]}
]

@NgModule({
  imports: [RouterModule.forChild(professorRoutes)],
  exports: [RouterModule]
})
export class ProfessorRoutingModule{}
