package com.example.demo.appbankevensoucqrs.commons.dtos;

import com.example.demo.appbankevensoucqrs.query.entities.Account;
import com.example.demo.appbankevensoucqrs.query.entities.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@AllArgsConstructor
@Getter
public class AccountStatement {
    private Account account;
    private List<Operation> operations;
}