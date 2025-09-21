package com.example.demo.appbankevensoucqrs.query.controllers;

import com.example.demo.appbankevensoucqrs.commons.dtos.AccountStatement;
import com.example.demo.appbankevensoucqrs.query.dtos.AccountEvent;
import com.example.demo.appbankevensoucqrs.query.entities.Account;
import com.example.demo.appbankevensoucqrs.query.queries.GetAccountStatement;
import com.example.demo.appbankevensoucqrs.query.queries.GetAllAccounts;
import com.example.demo.appbankevensoucqrs.query.queries.WatchEventQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/query/accounts")
public class AccountQueryController {

    private QueryGateway queryGateway;

    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/all")
    public CompletableFuture<List<Account>> getAllAccounts(){
        CompletableFuture<List<Account>> result = queryGateway.query(new GetAllAccounts(), ResponseTypes.multipleInstancesOf(Account.class));
        return result;
    }
    @GetMapping("/statement/{accountId}")
    public CompletableFuture<AccountStatement> getAccountStatement(@PathVariable String accountId){
        CompletableFuture<AccountStatement> result = queryGateway.query(new GetAccountStatement(accountId), ResponseTypes.instanceOf(AccountStatement.class));
        return result;
    }
    // pour la vesalisation des evenements en frontend
    @GetMapping(value = "/watch/{accountId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountEvent> watch(@PathVariable String accountId){
        SubscriptionQueryResult<AccountEvent, AccountEvent> result = queryGateway.subscriptionQuery(new WatchEventQuery(accountId),
                ResponseTypes.instanceOf(AccountEvent.class),
                ResponseTypes.instanceOf(AccountEvent.class)
        );
        return result.initialResult().concatWith(result.updates());
    }

}
