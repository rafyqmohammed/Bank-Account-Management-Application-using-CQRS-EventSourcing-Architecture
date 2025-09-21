package com.example.demo.appbankevensoucqrs.query.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class GetAccountStatement {
    private String accountId;
}
