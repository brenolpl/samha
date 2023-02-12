import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {AlocacaoComponent} from "./alocacao-edit/alocacao.component";
import {AlocacaoFormComponent} from "./alocacao-form/alocacao-form.component";
import {AlocacaoMainComponent} from "./alocacao-main/alocacao-main.component";


export const alocacaoRoutes: Routes = [
  {path: '', children: [
      {path: '', component: AlocacaoMainComponent},
      {path: 'new', component: AlocacaoFormComponent}
    ]
  }
]


@NgModule({
  imports: [RouterModule.forChild(alocacaoRoutes)],
  exports: [RouterModule]
})
export class AlocacaoRoutingModule {}
