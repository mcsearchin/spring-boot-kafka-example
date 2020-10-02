package com.bank.integration.pretend.stream.listener;

import com.bank.integration.pretend.model.ExternalModel;
import com.bank.integration.pretend.model.InternalModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import java.net.URI;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
class ExampleListenerTests {

    @Resource
    private Processor processor;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private ObjectMapper objectMapper;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void sendsTransformedDataToAPI() throws Exception {
        InternalModel internalModel = InternalModel.builder().internalField("testing").build();
        String expectedBody = objectMapper.writeValueAsString(
                ExternalModel.builder().externalField(internalModel.getInternalField()).build()
        );
        mockServer.expect(requestTo(new URI("http://www.example.com/test")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().string(expectedBody))
                .andRespond(withStatus(HttpStatus.OK));

        Message<InternalModel> message = new GenericMessage<>(internalModel);
        processor.input().send(message);

        mockServer.verify();
    }
}
