package br.com.zumbolovsky.fateapp.web

import javax.validation.constraints.Email

data class UserVO(
    var user: String? = null,
    @Email var email: String? = null,
    var password: String? = null)
