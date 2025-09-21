import {Component, OnInit} from '@angular/core';
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [],
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.css'
})
export class NotificationsComponent implements OnInit{
  notifications : Array<any> =[];
  constructor() {
  }
  ngOnInit() {
    let eventSource = new EventSource(environment.backendHost + `/query/accounts/watch`);
    eventSource.addEventListener("message", (event) => {
      //console.log(event.data);
      const eventData = JSON.parse(event.data);
      if(eventData.type){
        this.notifications.push(eventData);
      }
    });
  }

}
