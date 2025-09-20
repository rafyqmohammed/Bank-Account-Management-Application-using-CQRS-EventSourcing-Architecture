package com.example.demo.appbankevensoucqrs.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class DebitAccountCommand{
    @TargetAggregateIdentifier
    private String id;
    private double amount;
}