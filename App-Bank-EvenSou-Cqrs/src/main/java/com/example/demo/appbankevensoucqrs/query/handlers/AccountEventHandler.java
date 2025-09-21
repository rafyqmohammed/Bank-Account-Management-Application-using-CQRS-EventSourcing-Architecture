package com.example.demo.appbankevensoucqrs.query.handlers;

import com.example.demo.appbankevensoucqrs.commons.events.AccountCreatedEvent;
import com.example.demo.appbankevensoucqrs.commons.events.AccountCreditedEvent;
import com.example.demo.appbankevensoucqrs.commons.events.AccountDebitedEvent;
import com.example.demo.appbankevensoucqrs.commons.events.AccountStatusUpdatedEvent;
import com.example.demo.appbankevensoucqrs.query.dtos.AccountEvent;
import com.example.demo.appbankevensoucqrs.query.entities.Account;
import com.example.demo.appbankevensoucqrs.query.entities.Operation;
import com.example.demo.appbankevensoucqrs.query.entities.OperationType;
import com.example.demo.appbankevensoucqrs.query.repository.AccountRepository;
import com.example.demo.appbankevensoucqrs.query.repository.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountEventHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private QueryUpdateEmitter queryUpdateEmitter;

    public AccountEventHandler(AccountRepository accountRepository, OperationRepository operationRepository, QueryUpdateEmitter queryUpdateEmitter) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage){
        log.info("################# AccountCreatedEvent ################");
        Account account = Account.builder()
                .id(event.accountId())
                .balance(event.initialBalance())
                .currency(event.currency())
                .status(event.accountStatus())
                .createdAt(eventMessage.getTimestamp())
                .build();
        accountRepository.save(account);
        AccountEvent accountEvent = AccountEvent.builder()
                .type(event.getClass().getSimpleName())
                .accountId(account.getId())
                .balance(account.getBalance())
                .amount(event.initialBalance())
                .status(account.getStatus().toString())
                .build();
        queryUpdateEmitter.emit(e->true, accountEvent);

    }

    @EventHandler
    public void on(AccountStatusUpdatedEvent event, EventMessage eventMessage){
        log.info("################# AccountStatusUpdatedEvent ################");
        Account account = accountRepository.findById(event.accountId()).get();
        account.setStatus(event.toStatus());
        accountRepository.save(account);

        AccountEvent accountEvent = AccountEvent.builder()
                .type(event.getClass().getSimpleName())
                .accountId(account.getId())
                .balance(account.getBalance())
                .amount(0)
                .status(account.getStatus().toString())
                .build();
        queryUpdateEmitter.emit(e->true, accountEvent);

    }
    @EventHandler
    public void on(AccountDebitedEvent event, EventMessage eventMessage){
        log.info("################# AccountDebitedEvent ################");
        Account account = accountRepository.findById(event.accountId()).get();
        Operation operation = Operation.builder()
                .date(eventMessage.getTimestamp())
                .amount(event.amount())
                .type(OperationType.DEBIT)
                .account(account)
                .build();
        Operation savedOperation = operationRepository.save(operation);
        account.setBalance(account.getBalance()-operation.getAmount());
        accountRepository.save(account);
       AccountEvent accountEvent = AccountEvent.builder()
                .type(event.getClass().getSimpleName())
                .accountId(account.getId())
                .balance(account.getBalance())
                .amount(event.amount())
                .status(account.getStatus().toString())
                .build();
        queryUpdateEmitter.emit(e->true, accountEvent);

    }
    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage eventMessage){
        log.info("################# AccountCreditedEvent ################");
        Account account = accountRepository.findById(event.accountId()).get();
        Operation operation = Operation.builder()
                .date(eventMessage.getTimestamp())
                .amount(event.amount())
                .type(OperationType.CREDIT)
                .account(account)
                .build();
        Operation savedOperation = operationRepository.save(operation);
        account.setBalance(account.getBalance()+operation.getAmount());
        accountRepository.save(account);
        AccountEvent accountEvent = AccountEvent.builder()
                .type(event.getClass().getSimpleName())
                .accountId(account.getId())
                .balance(account.getBalance())
                .amount(event.amount())
                .status(account.getStatus().toString())
                .build();
        queryUpdateEmitter.emit(e->true, accountEvent);

    }


}
