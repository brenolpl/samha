import {RouterModule, Routes} from '@angular/router';
import {TableComponent} from '../shared/table/table.component';
import {CoordenadoriaFormComponent} from './coordenadoria-form/coordenadoria-form.component';
import {FormResolver} from '../guards/form-resolver';
import {NgModule} from '@angular/core';
import {LogComponent} from '../shared/log/log.component';

export const cooordenadoriaRoutes: Routes = [
  {path: '', children: [
      {path: '', component: TableComponent},
      {path: 'new', component: CoordenadoriaFormComponent},
      {path: 'log', component: LogComponent},
      {path: ':entity', component: CoordenadoriaFormComponent, resolve: {coord: FormResolver}}
    ]}
]

@NgModule({
  imports: [RouterModule.forChild(cooordenadoriaRoutes)],
  exports: [RouterModule]
})
export class CoordenadoriaRoutingModule {}
