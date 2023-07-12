package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.domain.postgres.Role
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserVO(
    @field:NotBlank(message = "{user.name} {not.blank}")
    val user: String?,
    @field:NotBlank(message = "{user.email} {not.blank}")
    @field:Email(message = "{user.email} {email.validation}")
    val email: String?,
    @field:NotBlank(message = "{user.password} {not.blank}")
    val password: String?) {
    internal fun toUserInfo(role: Role) =
        UserInfo(
            user = user,
            email = email,
            password = password,
            roles = listOf(role)
        )
    companion object {
        fun fromUserInfo(userInfo: UserInfo) =
            UserVO(
                user = userInfo.user,
                email = userInfo.email,
                password = null
            )
    }
}
