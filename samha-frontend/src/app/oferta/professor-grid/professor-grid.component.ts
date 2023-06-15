import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {DataService} from "../../shared/service/data.service";
import {Observable, range} from "rxjs";
import {Page, PagedList} from "../../shared/paged-list";
import {Filter, QueryMirror} from "../../shared/query-mirror";
import {first, map, tap, toArray} from "rxjs/operators";

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
  public aulasConflitantes: any[] = [];
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
        .selectList(['id', 'numero', 'dia', 'turno', 'oferta', 'alocacao.id', 'alocacao.disciplina', 'alocacao.professor1', 'alocacao.professor2', 'alocacao.ano', 'alocacao.semestre'])
        .where({
          or: {
            'alocacao.professor1.id': {equals: this.alocacao?.professor1?.id},
            ...professor2Predicate
          },
          and: {
            'oferta.semestre': {equals: this.semestre},
            'oferta.ano': {equals: this.ano}
          }
        }))
        .pipe(
          tap((next: PagedList) => {
            if (next.listMap.length > 0) {
              this.dataService.post('aula/obter-restricoes', next.listMap).pipe(first()).subscribe(
                next => {
                  next.forEach(conflito => {
                    conflito.mensagens.forEach(mensagem => {
                      let aulas = mensagem.aulas.map(a => Object.assign(a, {tipo: mensagem.tipo}));
                      this.aulasConflitantes.push(...aulas);
                    })
                  });
                }
              )
            }
          })
        );
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

  verificarAula(item: any): string {
    if (!(typeof item === 'string')) {
      let aula;

      if(item.alocacao.disciplina.tipo === 'ESPECIAL') {
        aula = this.aulasConflitantes.find(a => a.numero === item.numero &&
          a.dia === item.dia &&
          a.turno === item.turno &&
          a.alocacao.professor1?.id === item.alocacao.professor1.id &&
          a.alocacao.professor2?.id === item.alocacao.professor2?.id);
      } else {
        aula = this.aulasConflitantes.find(a => a.numero === item.numero &&
          a.dia === item.dia &&
          a.turno === item.turno &&
          a.alocacao.professor1?.id === item.alocacao.professor1.id);
      }
      if (aula !== undefined) {
        switch (aula.tipo as number) {
          case 1: return 'background-red';
          case 2: return 'background-yellow';
          case 3: return 'background-blue';
        }
      }
    }
    return 'background-white';
  }
}
