import {Component, OnInit} from '@angular/core';
import {LocalStorageService} from './shared/local-storage.service';

@Component({
  selector: 'samha-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  constructor(private localStorage: LocalStorageService) {
  }
  ngOnInit(): void {
    //this.localStorage.clear();
  }
  title = 'SAMHA Web';
}
