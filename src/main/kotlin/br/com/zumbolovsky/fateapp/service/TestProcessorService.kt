package br.com.zumbolovsky.fateapp.service

import org.springframework.plugin.core.Plugin
import org.springframework.stereotype.Component

@Component
interface TestProcessorService : Plugin<TestEnum> {
    fun process(): String
}