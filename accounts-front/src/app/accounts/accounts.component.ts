import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent implements OnInit{
  accounts : any;

  constructor(private http : HttpClient) {
  }
  ngOnInit() {
    this.http.get(environment.backendHost+ "/query/accounts/all").subscribe({
      next : resp => {
        this.accounts = resp
      }, error : err => {
        console.log(err);
      }
    })
  }
}
