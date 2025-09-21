package com.example.demo.appbankevensoucqrs.query.handlers;

import com.example.demo.appbankevensoucqrs.commons.dtos.AccountStatement;
import com.example.demo.appbankevensoucqrs.query.dtos.AccountEvent;
import com.example.demo.appbankevensoucqrs.query.entities.Account;
import com.example.demo.appbankevensoucqrs.query.entities.Operation;
import com.example.demo.appbankevensoucqrs.query.queries.GetAccountStatement;
import com.example.demo.appbankevensoucqrs.query.queries.GetAllAccounts;
import com.example.demo.appbankevensoucqrs.query.queries.WatchEventQuery;
import com.example.demo.appbankevensoucqrs.query.repository.AccountRepository;
import com.example.demo.appbankevensoucqrs.query.repository.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.GenericDomainEventMessage;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountQueryHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }
    @QueryHandler
    public List<Account> on(GetAllAccounts query){
        return accountRepository.findAll();
    }
    @QueryHandler
    public AccountStatement on(GetAccountStatement query){
        Account account = accountRepository.findById(query.getAccountId()).get();
        List<Operation> operations = operationRepository.findByAccountId(query.getAccountId());
        return new AccountStatement(account, operations);
    }

    @QueryHandler
    public AccountEvent on(WatchEventQuery query){
        return AccountEvent.builder().build();
    }


}