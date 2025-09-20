package com.example.demo.appbankevensoucqrs.commons.events;

public record AccountCreditedEvent(String accountId, double amount) {
}
