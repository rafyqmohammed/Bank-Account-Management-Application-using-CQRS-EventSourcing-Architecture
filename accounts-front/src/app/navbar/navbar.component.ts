import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {NotificationsComponent} from "../notifications/notifications.component";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    NotificationsComponent
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

}
