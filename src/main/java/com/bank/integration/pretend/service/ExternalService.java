package com.bank.integration.pretend.service;

import com.bank.integration.pretend.model.ExternalModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class ExternalService {
    private static final String URL = "http://www.example.com/test";

    @Resource
    private RestTemplate restTemplate;

    public void send(ExternalModel externalModel) {
        restTemplate.postForEntity(URL, externalModel, String.class);
    }
}
