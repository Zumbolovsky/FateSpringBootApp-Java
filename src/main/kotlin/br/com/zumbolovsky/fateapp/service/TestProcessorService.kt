package br.com.zumbolovsky.fateapp.service

import org.springframework.plugin.core.Plugin

interface TestProcessorService : Plugin<TestEnum> {
    fun process(): String
}