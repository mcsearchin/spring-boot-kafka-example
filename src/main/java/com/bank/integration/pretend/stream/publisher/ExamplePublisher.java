package com.bank.integration.pretend.stream.publisher;

import com.bank.integration.pretend.model.InternalModel;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ExamplePublisher {

    @Resource
    private Processor processor;

    public void publish(InternalModel internalModel) {
        processor.output().send(MessageBuilder.withPayload(internalModel).build());
    }
}
