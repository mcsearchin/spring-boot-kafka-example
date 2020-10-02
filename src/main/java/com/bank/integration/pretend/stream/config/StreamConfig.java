package com.bank.integration.pretend.stream.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;

@EnableBinding(Processor.class)
public class StreamConfig {
}
