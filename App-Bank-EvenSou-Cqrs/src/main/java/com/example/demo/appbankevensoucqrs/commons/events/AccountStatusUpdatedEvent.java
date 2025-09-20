package com.example.demo.appbankevensoucqrs.commons.events;

import com.example.demo.appbankevensoucqrs.commons.enums.AccountStatus;

public record AccountStatusUpdatedEvent(String accountId, AccountStatus fromStatus, AccountStatus toStatus) {
}
