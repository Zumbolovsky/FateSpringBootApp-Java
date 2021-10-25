package br.com.zumbolovsky.fateapp

import java.util.function.BinaryOperator

class KotlinFunctionService {

    companion object {

        @JvmStatic
        fun main(vararg: Array<String>) {
            println(functionTestMethod({a, b -> a.plus(b)}, "Olá", "Mundo!"))
            val function: BinaryOperator<String> =
                BinaryOperator<String> { a, b ->
                    if (b.length > 1) {
                        return@BinaryOperator "Muitos caracteres!"
                    }
                    val get = b[0]
                    a.find { it == get }.toString()
            }
            println(functionTestMethod(function, "Olá", "á"))
            println(functionTestMethod(function, "Até", "ás"))
            println("amigo".getAsInt())
            println("10".getAsInt())
            println("bab".isContainedInAnyOf("tigre", "bobao"))
            println("ab".isContainedInAnyOf("amigo", "abathur"))
        }

        private fun String.isContainedInAnyOf(vararg x: String): Int {
            for (z in x) {
                if (z.contains(this)) {
                    return x.indexOf(z)
                }
            }
            return -1
        }

        private fun String.getAsInt(): Int {
            return if (this.matches(Regex("[0-9]+"))) this.toInt() else 0
        }

        private fun functionTestMethod(
            func: BinaryOperator<String>,
            x: String,
            y: String): String {
            return func.apply(x, y)
        }
    }

}