import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {OfertaComponent} from "./oferta-main/oferta.component";
import {LogComponent} from "../shared/log/log.component";
import {OfertaGridComponent} from "./oferta-grid/oferta-grid.component";

export const ofertaRoutes: Routes = [
  {path: '', children: [
      {path: '', component: OfertaComponent},
      {path: 'log', component: LogComponent},
      {path: 'grid', component: OfertaGridComponent}
    ]}
]


@NgModule({
  imports: [RouterModule.forChild(ofertaRoutes)],
  exports: [RouterModule]
})
export class OfertaRoutingModule {}
