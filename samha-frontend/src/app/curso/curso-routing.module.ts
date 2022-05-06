import {RouterModule, Routes} from '@angular/router';
import {TableComponent} from '../shared/table/table.component';
import {NgModule} from '@angular/core';
import {CursoFormComponent} from './curso-form/curso-form.component';
import {FormResolver} from '../guards/form-resolver';

export const cursoRoutes: Routes = [
  {path: '', children: [
      {path: '', component: TableComponent},
      {path: 'new', component: CursoFormComponent},
      {path: ':entity', component: CursoFormComponent, resolve: {curso: FormResolver}}
    ]}
]

@NgModule({
  imports: [RouterModule.forChild(cursoRoutes)],
  exports: [RouterModule]
})
export class CursoRoutingModule {}
