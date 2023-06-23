import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Observable} from "rxjs";
import {DataService} from "../../shared/service/data.service";
import {QueryMirror} from "../../shared/query-mirror";
import {alocacaoColumns} from "../../meta-model/alocacao";
import {PagedList} from "../../shared/paged-list";

@Component({
  selector: 'samha-carga-horaria-modal',
  templateUrl: './carga-horaria.modal.component.html',
  styleUrls: ['./carga-horaria.modal.component.css']
})
export class CargaHorariaModalComponent implements OnInit {
  @Input() showPopup: boolean;
  @Output() showPopupChange = new EventEmitter<boolean>();
  @Input() cargaHoraria$: Observable<any>;
  @Input() ano: number;
  @Input() semestre: number;
  public showAlocacaoPopup: boolean = false;
  public selectedIndex = -1;
  public selectedrow: any;
  public alocacoesProfessor$: Observable<any>;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
  }

  onRowClicked(professor: any) {
    this.selectedIndex = professor.id
    this.selectedrow = professor;
    const request = {
      profId: professor.id,
      ano: this.ano,
      semestre: this.semestre
    }
    this.alocacoesProfessor$ = this.dataService.post('alocacao/obter-alocacoes-professor', request);
    this.showAlocacaoPopup = true;
  }
}
