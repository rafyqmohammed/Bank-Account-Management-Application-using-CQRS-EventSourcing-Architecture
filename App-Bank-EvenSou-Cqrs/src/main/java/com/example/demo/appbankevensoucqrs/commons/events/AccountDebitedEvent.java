package com.example.demo.appbankevensoucqrs.commons.events;

public record AccountDebitedEvent(String accountId, double amount) {
}