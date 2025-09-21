package com.example.demo.appbankevensoucqrs.query.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.stereotype.Service;

//@Service
@AllArgsConstructor
@Slf4j
public class ReplayService {
    private EventProcessingConfiguration eventProcessingConfiguration;

    public void reply(){
        String name="com.example.demo.appbankevensoucqrs.query.handlers";
        eventProcessingConfiguration.eventProcessor(name, TrackingEventProcessor.class)
                .ifPresent(processor->{
                    processor.shutDown();
                    processor.resetTokens();
                    processor.start();
                });
    }
}