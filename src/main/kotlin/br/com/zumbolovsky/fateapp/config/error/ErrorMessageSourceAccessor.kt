package br.com.zumbolovsky.fateapp.config.error

import org.springframework.context.support.MessageSourceAccessor

class ErrorMessageSourceAccessor(
    private val messageSourceAccessor: MessageSourceAccessor
) {

    fun getMessage(code: String, args: Array<Any>): String =
        messageSourceAccessor.getMessage(code, args)

    fun convertArgs(args: List<Any>): Array<Any> =
        if (args.isEmpty())
            emptyArray()
        else args.stream()
            .map { arg: Any ->
                if (arg is String)
                    messageSourceAccessor.getMessage(arg.toString(), arg.toString())
                else
                    arg
        }
        .toArray()

    fun getMessage(code: String): String {
        return messageSourceAccessor.getMessage(code)
    }
}