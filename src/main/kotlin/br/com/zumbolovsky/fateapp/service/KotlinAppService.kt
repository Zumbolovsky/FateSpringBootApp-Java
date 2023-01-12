package br.com.zumbolovsky.fateapp.service

import org.springframework.plugin.core.PluginRegistry
import org.springframework.stereotype.Service

@Service
class KotlinAppService(
    private val kotlinOtherService: KotlinOtherService,
    private val testProcessorPluginRegistry: PluginRegistry<TestProcessorService, TestEnum>
) {

    fun calculateAll(valores: Array<Int>?): Int {
        if (valores.isNullOrEmpty()) {
            throw RuntimeException("Valores inválidos")
        }
        val sumOf = valores.sumOf {
            kotlinOtherService.calculate(it, 2)
        }
        if (sumOf == 0) {
            throw RuntimeException("Erro de cálculo")
        }
        return sumOf
    }

    fun testPluginRegistry(): String =
        testProcessorPluginRegistry.getPluginFor(TestEnum.TEST_C)
            .orElseThrow {
                ImplementationNotFoundException(
                    debugArgs = listOf(TestProcessorService::class.simpleName!!, TestEnum.TEST_C.name))
            }
            .process()
            .also { println(it) }
}