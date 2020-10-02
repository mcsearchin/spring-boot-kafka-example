package com.bank.integration.pretend.stream.listener;

import com.bank.integration.pretend.model.ExternalModel;
import com.bank.integration.pretend.model.InternalModel;
import com.bank.integration.pretend.service.ExternalService;
import com.bank.integration.pretend.transform.ExampleTransformer;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ExampleListener {

    @Resource
    private ExampleTransformer transformer;
    @Resource
    private ExternalService externalService;

    @StreamListener(Processor.INPUT)
    public void process(InternalModel internalModel) {
        ExternalModel externalModel = transformer.toExternal(internalModel);
        externalService.send(externalModel);
    }
}
