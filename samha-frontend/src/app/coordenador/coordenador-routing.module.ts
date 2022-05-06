import {RouterModule, Routes} from '@angular/router';
import {TableComponent} from '../shared/table/table.component';
import {NgModule} from '@angular/core';
import {ProfessorFormComponent} from '../professor/professor-form/professor-form.component';
import {FormResolver} from '../guards/form-resolver';


export const coordenadorRoutes: Routes = [
  {path: '', children: [
      {path: '', component: TableComponent},
      {path: ':entity', component: ProfessorFormComponent, resolve: {professor: FormResolver}}
    ]}
]


@NgModule({
  imports: [RouterModule.forChild(coordenadorRoutes)],
  exports: [RouterModule]
})
export class CoordenadorRoutingModule {}
