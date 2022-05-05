import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TableComponent} from '../shared/table/table.component';
import {ProfessorFormComponent} from './professor-form/professor-form.component';
import {CanDeactivateGuard} from '../guards/can-deactivate-guard';
import {FormResolver} from '../guards/form-resolver';
import {RestricaoComponent} from './restricao/restricao.component';

export const professorRoutes: Routes = [
  { path: '', children: [
      {path: '', component: TableComponent},
      {path: 'new', component: ProfessorFormComponent, canDeactivate: [CanDeactivateGuard]},
      {path: ':entity', children:[
          {path: '', component: ProfessorFormComponent, resolve: {professor: FormResolver}},
          {path :'restricaoProfessor', children: [
              {path: 'new', component: RestricaoComponent},
              {path: ':target', component: RestricaoComponent, resolve: {restricao: FormResolver}}
            ]}
        ]},
    ]}
]

@NgModule({
  imports: [RouterModule.forChild(professorRoutes)],
  exports: [RouterModule]
})
export class ProfessorRoutingModule{}
