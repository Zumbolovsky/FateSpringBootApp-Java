package br.com.zumbolovsky.fateapp

import br.com.zumbolovsky.fateapp.domain.TestEnum
import org.springframework.stereotype.Component

@Component
class TestBProcessorService : TestProcessorService {
    override fun process(): String {
        return "I'm implementation B"
    }

    override fun supports(delimiter: TestEnum): Boolean {
        return TestEnum.TEST_B == delimiter
    }
}