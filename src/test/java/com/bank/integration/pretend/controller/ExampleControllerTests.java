package com.bank.integration.pretend.controller;

import com.bank.integration.pretend.model.ExternalModel;
import com.bank.integration.pretend.model.InternalModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ExampleControllerTests {

    @Resource
    private WebApplicationContext context;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private MessageCollector messageCollector;
    @Resource
    private Processor processor;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void publishesTransformedMessageToStream() throws Exception {
        ExternalModel externalModel = ExternalModel.builder().externalField("testing").build();
        String payload = objectMapper.writeValueAsString(externalModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/example")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        mvc.perform(requestBuilder).andExpect(status().isOk());

        Message<?> message = messageCollector.forChannel(processor.output()).poll();

        assertThat(message,  notNullValue());
        String expected = objectMapper.writeValueAsString(
                InternalModel.builder().internalField(externalModel.getExternalField()).build()
        );
        assertThat(message.getPayload(), equalTo(expected));
    }
}
