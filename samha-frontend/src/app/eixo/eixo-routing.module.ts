import {RouterModule, Routes} from '@angular/router';
import {TableComponent} from '../shared/table/table.component';
import {NgModule} from '@angular/core';
import {ProfessorFormComponent} from '../professor/professor-form/professor-form.component';
import {FormResolver} from '../guards/form-resolver';
import {EixoFormComponent} from './eixo-form/eixo-form.component';
import {LogComponent} from '../shared/log/log.component';


export const eixoRoutes: Routes = [
  {path: '', children: [
      {path: '', component: TableComponent},
      {path: 'new', component: EixoFormComponent},
      {path: 'log', component: LogComponent},
      {path: ':entity', component: EixoFormComponent, resolve: {eixo: FormResolver}}
    ]}
]


@NgModule({
  imports: [RouterModule.forChild(eixoRoutes)],
  exports: [RouterModule]
})
export class EixoRoutingModule {}
