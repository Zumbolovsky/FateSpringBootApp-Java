package br.com.zumbolovsky.fateapp.web

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull


data class UserVO(
    @field:NotNull(message = "{user.name} {not.null}")
    val user: String,
    @field:NotNull(message = "{user.email} {not.null}")
    @field:Email(message = "{user.email} {email.validation}")
    val email: String,
    @field:NotNull(message = "{user.password} {not.null}")
    val password: String)
