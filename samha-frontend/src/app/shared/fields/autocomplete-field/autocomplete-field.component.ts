import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {first, map, startWith} from 'rxjs/operators';
import {DataService} from '../../service/data.service';

@Component({
  selector: 'samha-autocomplete-field',
  templateUrl: './autocomplete-field.component.html',
  styleUrls: ['./autocomplete-field.component.css']
})
export class AutocompleteFieldComponent implements OnInit {
  @Input() resource: string;
  @Input() control: FormControl;
  @Input() label: string;
  @Output() onChange = new EventEmitter<any>();
  filteredOptions: Observable<any[]>;
  list: any[];

  constructor(private dataService: DataService) {}

  ngOnInit() {
    this.dataService.getAll(this.resource).pipe(first()).subscribe(
      next =>{
        this.list = next;
        this.filteredOptions = this.control.valueChanges.pipe(
          startWith(''),
          map(value => (typeof value === 'string' ? value : value?.name)),
          map(name => (name ? this._filter(name) : this.list.slice())),
        );
      }
    )

  }

  displayFn(entity: any): string {
    return entity && entity.nome ? entity.nome : '';
  }

  private _filter(nome: string): any[] {
    const filterValue = nome.toLowerCase();

    return this.list.filter(entity => entity.nome.toLowerCase().includes(filterValue));
  }

  onAutocompleteChange($event: any) {
    this.onChange.emit($event);
  }
}
