package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.service.RoleService
import br.com.zumbolovsky.fateapp.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
    private val roleService: RoleService
) {

    @GetMapping("/users")
    @Operation(summary = "Get all registered users.")
    fun findAll()  =
        userService.findAll().map { UserVO.fromUserInfo(it) }

    @PostMapping("/users")
    @Operation(summary = "Sign up a user with the information provided.")
    fun signUpUser(@RequestBody @Validated userVO: UserVO) : Unit =
        userVO.toUserInfo(roleService.findUserRole())
            .run { userService.signUp(this) }

    @PostMapping("/users/admin")
    @Operation(summary = "Sign up a admin user with the information provided.")
    fun signUpAdmin(@RequestBody @Validated userVO: UserVO) : Unit =
        userVO.toUserInfo(roleService.findAdminRole())
            .run { userService.signUp(this) }

}
