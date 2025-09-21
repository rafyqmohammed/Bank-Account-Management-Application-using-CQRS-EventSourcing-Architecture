import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {AccountChartComponent} from "../account-chart/account-chart.component";

@Component({
  selector: 'app-account-statement',
  standalone: true,
  imports: [
    FormsModule,
    AccountChartComponent
  ],
  templateUrl: './account-statement.component.html',
  styleUrl: './account-statement.component.css'
})
export class AccountStatementComponent implements OnInit{
  accountId: any ="dea35968-fee9-4043-8599-21320826ed6e";
  accountStatement : any;
  typeOperation: any;
  amount: number=0;

  constructor(private http : HttpClient) {
  }
  ngOnInit() {

  }

  getAccountStatement() {
    this.http.get(environment.backendHost+`/query/accounts/statement/${this.accountId}`)
      .subscribe({
        next : resp => {
          this.accountStatement = resp;
        } , error : err => {
          console.log(err);
        }
      });
  }

  addNewOperation() {
    let data = {accountId : this.accountId, amount : this.amount};
    let operationPath ="/commands/accounts"
    if(this.typeOperation==='DEBIT'){
      operationPath = operationPath + "/debit";
    } else {
      operationPath = operationPath + "/credit";
    }
    this.http.post(environment.backendHost+operationPath, data).subscribe({
      next : value => {
        this.getAccountStatement();
      }, error : err => {
        console.log(err);
      }
    })
  }
}
