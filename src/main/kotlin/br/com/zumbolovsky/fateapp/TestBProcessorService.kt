package br.com.zumbolovsky.fateapp

class TestBProcessorService : TestProcessorService {
    override fun process(): String {
        return "I'm implementation B"
    }

    override fun supports(delimiter: TestEnum): Boolean {
        return TestEnum.TEST_B == delimiter
    }
}