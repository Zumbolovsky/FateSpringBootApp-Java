package br.com.zumbolovsky.fateapp.service;

import org.springframework.stereotype.Component;

@Component
public class TestAProcessorService implements TestProcessorService {

    @Override
    public String process() {
        return "I'm implementation A";
    }

    @Override
    public boolean supports(TestEnum delimiter) {
        return TestEnum.TEST_A == delimiter;
    }
}