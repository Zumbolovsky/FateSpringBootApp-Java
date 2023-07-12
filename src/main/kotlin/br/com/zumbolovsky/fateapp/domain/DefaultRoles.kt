package br.com.zumbolovsky.fateapp.domain

import br.com.zumbolovsky.fateapp.config.error.ErrorArguments.ROLE
import br.com.zumbolovsky.fateapp.service.IllegalArgumentException

object DefaultRoles {
    const val USER = "USER"
    const val STAFF = "STAFF"
    const val ADMIN = "ADMIN"
    private val ALL_ROLES = listOf(USER, STAFF, ADMIN)
    fun valueOf(name: String) : String =
        ALL_ROLES.find { it == name }
            .takeIf { it != null }
            ?: throw IllegalArgumentException(debugArgs = listOf(name, ROLE))
}