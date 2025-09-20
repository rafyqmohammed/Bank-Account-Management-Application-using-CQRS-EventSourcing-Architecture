package com.example.demo.appbankevensoucqrs.commons.events;

import com.example.demo.appbankevensoucqrs.commons.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountActivatedEvent {
    private String id;
    private AccountStatus status;
}
