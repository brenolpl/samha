import {NgModule} from "@angular/core";
import {MaterialModule} from "../shared/material/material.module";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../shared/shared.module";
import {OfertaRoutingModule} from "./oferta-routing.module";
import {OfertaComponent} from "./oferta-main/oferta.component";
import {DragDropModule} from "@angular/cdk/drag-drop";
import {DxDraggableModule, DxSchedulerModule, DxScrollViewModule} from "devextreme-angular";
import { OfertaGridComponent } from './oferta-grid/oferta-grid.component';
@NgModule({
  declarations: [
    OfertaComponent,
    OfertaGridComponent
  ],
  imports: [
    OfertaRoutingModule,
    MaterialModule,
    CommonModule,
    SharedModule,
    DragDropModule,
    DxSchedulerModule,
    DxScrollViewModule,
    DxDraggableModule
  ]
})
export class OfertaModule {}
