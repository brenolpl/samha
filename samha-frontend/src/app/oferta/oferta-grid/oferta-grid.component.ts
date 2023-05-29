import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';

@Component({
  selector: 'samha-oferta-grid',
  templateUrl: './oferta-grid.component.html',
  styleUrls: ['./oferta-grid.component.css']
})
export class OfertaGridComponent implements OnInit, OnChanges {
  @Input() matriz: any[][] = [[]];
  @Input() novaAula: any;
  @Input() turno: string;
  @Output() public novaAulaCreated = new EventEmitter<any>();
  @Output() public  aulaIndexChanged = new EventEmitter<any>();
  highlightedRowIndex: number = -1;
  highlightedColIndex: number = -1;
  public horarioNoturno = [
    '18:50 a 19:35',
    '19:35 a 20:20',
    '20:30 a 21:15',
    '21:15 a 22:00',
    '-',
    '-'
  ];
  public horarioVespertino = [
    '12:50 a 13:40',
    '13:45 a 14:35',
    '14:40 a 15:30',
    '15:50 a 16:40',
    '16:45 a 17:35',
    '17:40 a 18:30'
  ];
  public horarioMatutino = [
    '07:00 a 07:50',
    '07:55 a 08:45',
    '08:50 a 09:40',
    '10:00 a 10:50',
    '10:55 a 11:45',
    '11:50 a 12:40'
  ]
  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges) {
  }

  onDragOver(event: DragEvent, rowIndex: number, colIndex: number) {
    event.preventDefault();
    this.highlightedRowIndex = rowIndex;
    this.highlightedColIndex = colIndex;
  }

  onDragLeave(event: DragEvent, rowIndex: number, colIndex: number) {
    event.preventDefault();
    if (this.highlightedRowIndex === rowIndex && this.highlightedColIndex === colIndex) {
      this.highlightedRowIndex = -1;
      this.highlightedColIndex = -1;
    }
  }
  onDragStart(event: DragEvent, rowIndex: number, colIndex: number) {
    const target = event.target as HTMLElement;
    target.classList.add('dragging');
    event.dataTransfer?.setData('text/plain', JSON.stringify({ rowIndex, colIndex }));
  }

  onDragEnd(event: DragEvent) {
    event.preventDefault();
    const target = event.target as HTMLElement;
    target.classList.remove('dragging');
  }

  onItemDrop(event: DragEvent, rowIndex: number, colIndex: number) {
    if(this.novaAula !== undefined) {
      let aula = {
        alocacao: this.novaAula.alocacao,
        id: null,
        dia: rowIndex,
        numero: colIndex + this.novaAula.turno,
        turno: this.novaAula.turno,
        oferta: this.novaAula.oferta,
        turma: this.novaAula.turma
      }
      this.novaAulaCreated.emit(aula);
    } else {
      const data = JSON.parse(event.dataTransfer?.getData('text/plain') || '');
      const prevRowIndex = data.rowIndex;
      const prevColIndex = data.colIndex;
      if (prevRowIndex === rowIndex && prevColIndex === colIndex) {
        return; // O elemento não mudou de posição
      }
      const prevItem = this.matriz[prevRowIndex][prevColIndex];
      const currItem = this.matriz[rowIndex][colIndex];
      if(currItem !== undefined && !(typeof currItem === 'string')) {
        currItem.dia = prevRowIndex;
        currItem.numero = prevColIndex + currItem.turno;
      }
      if(prevItem !== undefined && !(typeof prevItem === 'string')) {
        prevItem.dia = rowIndex;
        prevItem.numero = colIndex + prevItem.turno;
      }

      this.aulaIndexChanged.emit({
        prevItem: {
          prevRowIndex: prevRowIndex,
          prevColIndex: prevColIndex,
          item: prevItem
        },
        currItem: {
          rowIndex: rowIndex,
          colIndex: colIndex,
          item: currItem
        }
      })
    }

    this.highlightedRowIndex = -1;
    this.highlightedColIndex = -1;
  }
  onClick(event: MouseEvent, i: number, j: number) {
    event.preventDefault();
    if(this.highlightedRowIndex == i && this.highlightedColIndex == j) {
      this.highlightedColIndex = -1;
      this.highlightedRowIndex = -1;
    } else {
      this.highlightedColIndex = j;
      this.highlightedRowIndex = i;
    }
  }

  onKeyDown(event: KeyboardEvent, i: number, j: number) {
    if (event.key === 'Delete' || event.key === 'Backspace') {
      this.matriz[i][j] = undefined;
    }
  }

  getNomeEncurtadoProfessor(nome: string) {
    if(nome != null) {
      let nomes = nome.split(' ');
      let siglas = nomes.map(n => n.substring(0, 1)).splice(1).join('');
      let nomeEncutado = nomes[0] + ' ' + siglas;
      return nomeEncutado;
    }

    return '';
  }

  getHorarios(): string[] {
    switch (this.turno) {
      case 'Matutino':
        return this.horarioMatutino;
      case 'Vespertino':
        return this.horarioVespertino;
      case 'Noturno':
        return this.horarioNoturno;
      default: return [];
    }
  }

  getDia(i: number) {
    switch (i) {
      case 0:return 'Segunda-feira';
      case 1: return 'Terça-feira';
      case 2: return 'Quarta-feira';
      case 3: return 'Quinta-feira';
      case 4: return 'Sexta-feira';
      default: return 'Inexistente';
    }
  }
}
