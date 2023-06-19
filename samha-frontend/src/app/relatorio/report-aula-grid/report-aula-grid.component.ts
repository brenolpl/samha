import {Component, Input, OnInit} from '@angular/core';
import {Observable, range} from "rxjs";
import {map, toArray} from "rxjs/operators";

@Component({
  selector: 'samha-report-aula-grid',
  templateUrl: './report-aula-grid.component.html',
  styleUrls: ['./report-aula-grid.component.css', '../../oferta/oferta-grid/oferta-grid.component.css']
})
export class ReportAulaGridComponent {
  @Input() entityData: any[];
  @Input() ano: number;
  @Input() semestre: number;

  getMatrizAulasProfessor(aulasProfessor: any[]) {
    let matriz: any[][];
    const matriz$: Observable<any[][]> = range(0, 5).pipe(
      map(() => Array.from({ length: 16 }, () => '')),
      toArray()
    );
    matriz$.subscribe(m => matriz = m);
    aulasProfessor.forEach(a => matriz[a.dia][a.numero] = a);
    return matriz;
  }

  getDia(i: number) {
    switch (i) {
      case 0:return 'Segunda';
      case 1: return 'Terça';
      case 2: return 'Quarta';
      case 3: return 'Quinta';
      case 4: return 'Sexta';
      default: return 'Inexistente';
    }
  }

}
