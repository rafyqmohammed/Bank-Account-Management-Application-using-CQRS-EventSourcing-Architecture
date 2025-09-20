package com.example.demo.appbankevensoucqrs.commons.dtos;

import com.example.demo.appbankevensoucqrs.commons.enums.AccountStatus;

public record UpdateAccountStatusDTO(String accountId, AccountStatus accountStatus) {
}