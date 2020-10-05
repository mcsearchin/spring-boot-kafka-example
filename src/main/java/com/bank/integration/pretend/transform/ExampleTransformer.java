package com.bank.integration.pretend.transform;

import com.bank.integration.pretend.model.ExternalModel;
import com.bank.integration.pretend.model.InternalModel;
import org.springframework.stereotype.Component;

@Component
public class ExampleTransformer {

    public ExternalModel toExternal(InternalModel internal) {
        return ExternalModel.builder().externalField(internal.getInternalField()).build();
    }

    public InternalModel toInternal(ExternalModel external) {
        return InternalModel.builder().internalField(external.getExternalField()).build();
    }
}
