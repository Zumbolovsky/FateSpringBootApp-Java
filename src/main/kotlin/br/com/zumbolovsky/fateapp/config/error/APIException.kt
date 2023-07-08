package br.com.zumbolovsky.fateapp.config.error

import org.springframework.http.HttpStatus
import java.io.Serializable

abstract class APIException(
    args: List<String>? = null,
    debugArgs: List<String>? = null
) : RuntimeException(), Serializable {
    internal val messages = arrayListOf<String>()
    internal val additionalInfo = hashMapOf<String, Any>()
    internal val debugArgs = arrayListOf<Any>()
    internal val args = arrayListOf<Any>()

    init {
        args?.let { addArguments(it) }
        debugArgs?.let { addDebugArguments(it) }
    }

    val debugMessage: String?
        get() {
            val klass: Class<out APIException> = this.javaClass
            return if (klass.isAnnotationPresent(ErrorCode::class.java))
                klass.getAnnotation(ErrorCode::class.java).debugMessage
            else
                null
        }

    override val message: String?
        get() {
            val klass: Class<out APIException> = this.javaClass
            return if (klass.isAnnotationPresent(ErrorCode::class.java))
                klass.getAnnotation(ErrorCode::class.java).message
            else
                null
        }

    internal val status: HttpStatus
        get() {
            val klass: Class<out APIException> = this.javaClass
            return if (klass.isAnnotationPresent(ErrorStatus::class.java))
                klass.getAnnotation(ErrorStatus::class.java).value
            else
                HttpStatus.BAD_REQUEST
        }


    private fun addDebugArguments(values: List<Any>) {
        debugArgs.addAll(values)
    }

    private fun addArguments(values: List<Any>) {
        args.addAll(values)
    }

    fun addAditionalInfo(key: String, value: Any) {
        additionalInfo[key] = value
    }

    companion object {
        private const val serialVersionUID = -4014775134653608283L
    }
}