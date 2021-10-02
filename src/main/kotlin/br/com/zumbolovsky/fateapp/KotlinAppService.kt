package br.com.zumbolovsky.fateapp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.RuntimeException

@Service
class KotlinAppService(@Autowired var kotlinOtherService: KotlinOtherService) {

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
}