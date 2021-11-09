package br.com.zumbolovsky.fateapp

import br.com.zumbolovsky.fateapp.domain.TestEnum
import org.springframework.plugin.core.Plugin
import org.springframework.stereotype.Component

@Component
interface TestProcessorService : Plugin<TestEnum> {
    fun process(): String
}