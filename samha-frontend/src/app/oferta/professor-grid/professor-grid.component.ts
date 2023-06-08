import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {DataService} from "../../shared/service/data.service";
import {Observable, range} from "rxjs";
import {PagedList} from "../../shared/paged-list";
import {Filter, QueryMirror} from "../../shared/query-mirror";
import {map, toArray} from "rxjs/operators";

@Component({
  selector: 'samha-professor-grid',
  templateUrl: './professor-grid.component.html',
  styleUrls: ['../oferta-grid/oferta-grid.component.css']
})
export class ProfessorGridComponent implements OnChanges{
  @Input() public alocacao: any;
  @Input() public ofertaId: string;
  @Input() public ano: string;
  @Input() public semestre: string;
  public aulas$: Observable<PagedList>;

  constructor(private dataService: DataService) { }

  ngOnChanges(changes: SimpleChanges) {
    let professor2Predicate: Filter;
    if(changes?.alocacao?.currentValue) {
      if(changes.alocacao.currentValue.professor2?.id) {
        professor2Predicate = {
          or: {
            'alocacao.professor2.id': {equals: this.alocacao?.professor2?.id}
          }
        }
      }
    }

    if(this.alocacao) {
      this.aulas$ = this.dataService.query(new QueryMirror('aula')
        .selectList(['id', 'numero', 'dia', 'turno', 'oferta.turma.nome', 'alocacao.id', 'alocacao.professor1', 'alocacao.professor2'])
        .where({
          or: {
            'alocacao.professor1.id': {equals: this.alocacao?.professor1?.id},
            ...professor2Predicate
          },
          and: {
            'oferta.semestre': {equals: this.semestre},
            'oferta.ano': {equals: this.ano}
          }
        }));
    }
  }

  getDia(i: number) {
    switch (i) {
      case 0: return 'Segunda';
      case 1: return 'Terça';
      case 2: return 'Quarta';
      case 3: return 'Quinta';
      case 4: return 'Sexta';
      default: return 'Inexistente';
    }
  }

  getMatrizAulasProfessor(aulasProfessor: PagedList) {
    let aulas = aulasProfessor.listMap;
    let matriz: any[][];
    const matriz$: Observable<any[][]> = range(0, 5).pipe(
      map(() => Array.from({ length: 16 }, () => '')),
      toArray()
    );

    matriz$.subscribe(m => matriz = m);
    aulas.forEach(a => matriz[a.dia][a.numero] = a);
    return matriz;
  }
}
