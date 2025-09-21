import {Component, OnInit, ViewChild} from '@angular/core';
import {BaseChartDirective} from "ng2-charts";
import {environment} from "../../environments/environment";
import {ChartConfiguration, ChartOptions} from "chart.js";

@Component({
  selector: 'app-account-chart',
  standalone: true,
  imports: [
    BaseChartDirective
  ],
  templateUrl: './account-chart.component.html',
  styleUrl: './account-chart.component.css'
})
export class AccountChartComponent implements OnInit{
  @ViewChild(BaseChartDirective, { static: true }) chart!: BaseChartDirective;
  constructor() {
  }
  ngOnInit() {
    let eventSource = new EventSource(environment.backendHost + `/query/accounts/watch`);
    eventSource.addEventListener("message", (event) => {
      //console.log(event.data);
      const eventData = JSON.parse(event.data);
      if(eventData.type){
        this.lineChartData.labels?.push(eventData.accountId);
        this.lineChartData.datasets[0].data.push(eventData.amount);
        this.chart.update()
      }
    });
  }

  public lineChartData: ChartConfiguration['data'] = {
    labels: [
    ],

    datasets: [
      {
        data: [ ],
        label: 'Operation',
        fill: true,
        tension: 0.5,
        borderColor: 'black',
        backgroundColor: 'rgba(255,0,0,0.3)'
      },
      {
        data: [ ],
        label: 'React',
        fill: true,
        tension: 0.5,
        borderColor: 'black',
        backgroundColor: 'rgba(0,255,0,0.3)'
      }
    ]
  };

  public lineChartOptions: ChartOptions = {
    responsive: true
  };

  public lineChartLegend = true;


}
