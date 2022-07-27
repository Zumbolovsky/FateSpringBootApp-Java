package br.com.zumbolovsky.fateapp.web

import javax.validation.constraints.NotNull

data class AuthRequest(
    @field:NotNull(message = "{user.name} {not.null}")
    val user: String,
    @field:NotNull(message = "{user.password} {not.null}")
    val password: String)
