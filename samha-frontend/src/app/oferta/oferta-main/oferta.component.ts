import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DataService} from "../../shared/service/data.service";
import {NotificationService} from "../../shared/service/notification.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {first, map, startWith} from "rxjs/operators";
import {QueryMirror} from "../../shared/query-mirror";
import {alocacaoColumns} from "../../meta-model/alocacao";
import {
  CdkDragDrop,
  moveItemInArray,
  copyArrayItem,
  transferArrayItem,
  CdkDrag,
  CdkDropList
} from "@angular/cdk/drag-drop";


@Component({
  selector: 'samha-oferta',
  templateUrl: './oferta.component.html',
  styleUrls: ['./oferta.component.css']
})
export class OfertaComponent implements OnInit {
  public cursoControl = new FormControl();
  public turmaControl = new FormControl();
  public formGroup: FormGroup;
  public filteredOptions: Observable<any[]>;
  public alocacao$: Observable<any>;
  public alocacaoColumns = alocacaoColumns;
  public alocacaoDisplayedColumns = alocacaoColumns.filter(c => c.visible).map(c => c.columnDef);
  private list: any[];


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private dataService: DataService,
    private notification: NotificationService,
    private formBuilder: FormBuilder
  ) {
    this.formGroup = formBuilder.group({
      turno: ['Matutino'],
      ano: [new Date().getUTCFullYear()],
      semestre: [1]
    });
  }

  ngOnInit(): void {
  }

  goToLog() {
    this.router.navigate(['log'], {relativeTo: this.route});
  }

  onCursoChange(_) {
    this.loadTurmas();
  }

  onCursoLoaded($event: any[]) {
    this.cursoControl.setValue($event[0]);
    this.loadTurmas();
  }

  compareFunction = (o1: string, o2: string) => (o1 != null && o2 != null && o1.toUpperCase() == o2.toUpperCase());
  displayFn = (entity: any): string => entity && entity.nome ? entity.nome : '';

  private _filter(nome: string): any[] {
    const filterValue = nome.toLowerCase();
    return this.list.filter(entity => entity.nome.toLowerCase().includes(filterValue));
  }


  private loadTurmas() {
    this.dataService.query(
      new QueryMirror('turma')
        .selectList(['id', 'nome'])
        .where({
            and: {
              'turno': {equals: (this.formGroup.get('turno').value as string).toUpperCase()},
              'matriz.curso': {equals: this.cursoControl.value.id}
            }
          }
        )
    ).pipe(first()).subscribe(
      data => {
        this.list = data.listMap;
        if(this.list.length > 0) this.turmaControl.setValue(this.list[0]);
        this.filteredOptions = this.turmaControl.valueChanges.pipe(
          startWith(''),
          map(value => (typeof value === 'string' ? value : value?.name)),
          map(name => (name ? this._filter(name) : this.list.slice())),
        );
      }
    )
  }

  onTurnoChange() {
    this.loadTurmas();
  }

  onAnoChange() {

  }

  segundaArray = ['', '', '', '', '', ''];
  tercaArray = ['', '', '', '', '', ''];
  quartaArray = ['', '', '', '', '', ''];
  quintaArray = ['', '', '', '', '', ''];
  sextaArray = ['', '', '', '', '', ''];

  movies = [
    'Episode I - The Phantom Menace',
    'Episode II - Attack of the Clones',
    'Episode III - Revenge of the Sith',
    'Episode IV - A New Hope',
    'Episode V - The Empire Strikes Back',
    'Episode VI - Return of the Jedi',
    'Episode VII - The Force Awakens',
    'Episode VIII - The Last Jedi',
  ];

  horarios = [
    '18:50 a 19:35',
    '19:35 a 20:20',
    '20:30 a 21:15',
    '21:15 a 22:00',
    '-',
    '-'
  ];

  noReturnPredicate() {
    return false;
  }

  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else if (event.previousContainer.id === 'primary-list') {
      this.copyWithTradingArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex);
    } else {
      this.tradeArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      )
    }
  }

  tradeArrayItem(currentArray: any[], targetArray: any[], currentIndex: number, targetIndex: number) {
    let itemFromCurrentArray = currentArray[currentIndex];
    let itemFromTargetArray = targetArray[targetIndex];
    if(itemFromTargetArray !== undefined) {
      currentArray[currentIndex] = itemFromTargetArray;
      targetArray[targetIndex] = itemFromCurrentArray;
    }
  }

  copyWithTradingArrayItem(currentArray: any[], targetArray: any[], currentIndex: number, targetIndex: number) {
    let itemFromCurrentArray = currentArray[currentIndex];
    let itemFromTargetArray = targetArray[targetIndex];
    if(itemFromTargetArray !== undefined) {
      targetArray[targetIndex] = itemFromCurrentArray;
    }
  }
}
