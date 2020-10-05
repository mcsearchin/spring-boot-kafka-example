package com.bank.integration.pretend.controller;

import com.bank.integration.pretend.model.ExternalModel;
import com.bank.integration.pretend.model.InternalModel;
import com.bank.integration.pretend.stream.publisher.ExamplePublisher;
import com.bank.integration.pretend.transform.ExampleTransformer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ExampleController {

    @Resource
    private ExamplePublisher publisher;
    @Resource
    private ExampleTransformer transformer;

    @RequestMapping(value = "/api/example", method = RequestMethod.POST)
    public void post(@RequestBody ExternalModel externalModel) {
        InternalModel internalModel = transformer.toInternal(externalModel);
        publisher.publish(internalModel);
    }
}
