package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UserVO(
    @field:NotBlank(message = "{user.name} {not.blank}")
    val user: String?,
    @field:NotBlank(message = "{user.email} {not.blank}")
    @field:Email(message = "{user.email} {email.validation}")
    val email: String?,
    @field:NotBlank(message = "{user.password} {not.blank}")
    val password: String?) {
    internal fun toUserInfo() =
        UserInfo(
            user = user,
            email = email,
            password = password)
}
