import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {AlocacaoComponent} from "./alocacao-edit/alocacao.component";
import {AlocacaoFormComponent} from "./alocacao-form/alocacao-form.component";


export const alocacaoRoutes: Routes = [
  {path: '', children: [
      {path: '', component: AlocacaoComponent},
      {path: 'new', component: AlocacaoFormComponent}
    ]
  }
]


@NgModule({
  imports: [RouterModule.forChild(alocacaoRoutes)],
  exports: [RouterModule]
})
export class AlocacaoRoutingModule {}
