import {Component, OnInit} from '@angular/core';
import {DataService} from '../../shared/data.service';

@Component({
  selector: 'samha-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private dataService: DataService) {
  }

  ngOnInit(): void {
    this.dataService.get('api/' + 'alocacao', '293').subscribe(
      data => {
        console.log(data);
      }, error => {
        console.log(error);
      }
    );
  }

}
