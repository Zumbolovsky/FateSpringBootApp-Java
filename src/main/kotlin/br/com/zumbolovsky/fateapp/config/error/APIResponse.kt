package br.com.zumbolovsky.fateapp.config.error

import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.time.LocalDateTime

data class APIResponse<T>(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    val timestamp: LocalDateTime? = LocalDateTime.now(),
    val body: T? = null,
    val message: String? = null,
    var debugMessage: String? = null,
    val additionalInfo: HashMap<String, Any>? = hashMapOf()
) : Serializable {

    companion object {
        private const val serialVersionUID: Long = -7842207334955640260L
    }
}