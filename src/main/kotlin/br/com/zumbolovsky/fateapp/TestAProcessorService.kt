package br.com.zumbolovsky.fateapp

import org.springframework.stereotype.Component

@Component
class TestAProcessorService : TestProcessorService {
    override fun process(): String {
        return "I'm implementation A"
    }

    override fun supports(delimiter: TestEnum): Boolean {
        return TestEnum.TEST_A == delimiter
    }
}