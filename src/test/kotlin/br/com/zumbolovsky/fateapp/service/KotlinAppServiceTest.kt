package br.com.zumbolovsky.fateapp.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.springframework.plugin.core.PluginRegistry
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
internal class KotlinAppServiceTest {

    @Mock private lateinit var kotlinOtherService: KotlinOtherService

    @Mock private lateinit var testProcessorPluginRegistry: PluginRegistry<TestProcessorService, TestEnum>

    @InjectMocks private lateinit var kotlinAppService: KotlinAppService

    @Test
    fun `calculateAll should throw RuntimeException for invalid values`() {
        Assertions.assertThrows(RuntimeException::class.java) {
            kotlinAppService.calculateAll(null)
            kotlinAppService.calculateAll(arrayOf())
            kotlinAppService.calculateAll(arrayOf(0, 0, 0))
        }
    }

    @Test
    fun `calculateAll should assert values`() {
        doReturn(3).`when`(kotlinOtherService).calculate(1, 2)
        val valores = arrayOf(1, 1)
        Assertions.assertEquals(6, kotlinAppService.calculateAll(valores))
    }
}