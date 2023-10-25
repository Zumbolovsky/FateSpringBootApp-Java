package br.com.zumbolovsky.fateapp.service;

import org.springframework.stereotype.Component;

@Component
public class TestBProcessorService implements TestProcessorService {

    @Override
    public String process() {
        return "I'm implementation B";
    }

    @Override
    public boolean supports(TestEnum delimiter) {
        return TestEnum.TEST_B == delimiter;
    }
}