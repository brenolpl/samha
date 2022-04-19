import {Component, Input, OnInit} from '@angular/core';
import {DataService} from '../data.service';

@Component({
  selector: 'samha-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  @Input() resource: string;
  dataSource: any;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.getAll(this.resource).subscribe(
      (result) => {
        this.dataSource = result;
      }
    )
  }

}
