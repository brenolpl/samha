import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ofertaLogColumns} from "../../meta-model/oferta";

@Component({
  selector: 'samha-oferta-log',
  templateUrl: './oferta-log.component.html',
  styleUrls: ['./oferta-log.component.css']
})
export class OfertaLogComponent {
  public readonly ofertaLogColumns = ofertaLogColumns;
  constructor(private router: Router,
              private route: ActivatedRoute) { }
  goBack = () => this.router.navigate(['../'], {relativeTo: this.route});
}
