import {RouterModule, Routes} from '@angular/router';
import {UsuarioFormComponent} from './usuario-form/usuario-form.component';
import {NgModule} from '@angular/core';
import {TableComponent} from '../shared/table/table.component';
import {FormResolver} from '../guards/form-resolver';


export const usuarioRoutes: Routes = [
  {path: '', children: [
      {path: '', component: TableComponent},
      {path: 'new', component: UsuarioFormComponent},
      {path: ':entity', component: UsuarioFormComponent, resolve: {usuario: FormResolver}}
    ]}

]


@NgModule({
  imports: [RouterModule.forChild(usuarioRoutes)],
  exports: [RouterModule]
})
export class UsuarioRoutingModule {}
