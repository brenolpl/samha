import {RouterModule, Routes} from '@angular/router';
import {TableComponent} from '../shared/table/table.component';
import {NgModule} from '@angular/core';
import {FormResolver} from '../guards/form-resolver';
import {MatrizFormComponent} from "./matriz-form/matriz-form.component";


export const matrizRoutes: Routes = [
  {path: '', children: [
      {path: '', component: TableComponent},
      {path: 'new', component: MatrizFormComponent},
      {path: ':entity', component: MatrizFormComponent, resolve: {matriz: FormResolver}}
    ]}
]


@NgModule({
  imports: [RouterModule.forChild(matrizRoutes)],
  exports: [RouterModule]
})
export class MatrizRoutingModule {}
