package com.example.demo.appbankevensoucqrs.commons.events;

import com.example.demo.appbankevensoucqrs.commons.enums.AccountStatus;

public record AccountCreatedEvent(String accountId, double initialBalance, String currency, AccountStatus accountStatus) {
}
