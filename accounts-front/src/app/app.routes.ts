import { Routes } from '@angular/router';
import {AccountsComponent} from "./accounts/accounts.component";
import {AccountStatementComponent} from "./account-statement/account-statement.component";
import {NotificationsComponent} from "./notifications/notifications.component";

export const routes: Routes = [
  { path : "accounts", component : AccountsComponent},
  { path : "accountStatement", component : AccountStatementComponent},
  { path : "notifications", component : NotificationsComponent}
];
