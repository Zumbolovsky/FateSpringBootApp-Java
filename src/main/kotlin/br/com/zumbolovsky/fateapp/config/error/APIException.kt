package br.com.zumbolovsky.fateapp.config.error

import org.springframework.http.HttpStatus

abstract class APIException : RuntimeException {
    internal val messages = arrayListOf<String>()
    internal val additionalInfo = hashMapOf<String, Any>()

    val debugMessage: String?
        get() {
            val klass: Class<out APIException> = this.javaClass
            return if (klass.isAnnotationPresent(ErrorCode::class.java))
                klass.getAnnotation(ErrorCode::class.java).debugMessage
            else
                null
        }

    internal val debugArgs = arrayListOf<Any>()

    override val message: String?
        get() {
            val klass: Class<out APIException> = this.javaClass
            return if (klass.isAnnotationPresent(ErrorCode::class.java))
                klass.getAnnotation(ErrorCode::class.java).message
            else
                null
        }

    internal val args = arrayListOf<Any>()

    internal val status: HttpStatus
        get() {
            val klass: Class<out APIException> = this.javaClass
            return if (klass.isAnnotationPresent(ErrorStatus::class.java))
                klass.getAnnotation(ErrorStatus::class.java).value
            else
                HttpStatus.BAD_REQUEST
        }

    constructor() : super()

    constructor(messages: List<String>) : super(if (messages.isNotEmpty()) messages[0] else null) {
        this.messages.addAll(messages)
    }

    constructor(message: String) : super(message) {
        messages.add(message)
    }

    constructor(message: String, cause: Throwable) : super(message, cause) {
        messages.add(message)
    }

    fun addDebugArguments(value: Any) {
        debugArgs.add(value)
    }

    fun addDebugArguments(values: List<Any>) {
        debugArgs.addAll(values)
    }

    fun addArgument(value: Any) {
        args.add(value)
    }

    fun addArguments(values: List<Any>) {
        args.addAll(values)
    }

    fun addAditionalInfo(key: String, value: Any) {
        additionalInfo[key] = value
    }

    companion object {
        private const val serialVersionUID = -4014775134653608283L
    }
}