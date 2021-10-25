package br.com.zumbolovsky.fateapp

import org.springframework.plugin.core.Plugin

interface TestProcessorService : Plugin<TestEnum> {
    fun process(): String
}