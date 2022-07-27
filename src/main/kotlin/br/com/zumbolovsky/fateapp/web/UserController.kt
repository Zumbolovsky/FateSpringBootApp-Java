package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService) {

    @PostMapping("/users")
    @Operation(summary = "Sign up a user with the information provided.")
    fun signUp(@RequestBody @Validated userVO: UserVO) {
        val userInfo = userVO.toUserInfo()
        userService.signUp(userInfo)
    }

}
