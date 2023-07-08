package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.service.RoleService
import br.com.zumbolovsky.fateapp.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
    private val roleService: RoleService
) {

    @GetMapping("/users/{role}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    @Operation(summary = "Get all registered users by role.")
    fun findAllByRole(@PathVariable("role") role: String) : List<UserVO> =
        userService.findAllByRole(role).map { UserVO.fromUserInfo(it) }

    @PostMapping("/users/{role}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @Operation(summary = "Sign up a user with specific role and registration information.")
    fun signUpByRole(@PathVariable("role") role: String, @RequestBody @Validated userVO: UserVO) : Unit =
        userVO.toUserInfo(roleService.findRoleByName(role))
            .run { userService.signUp(this) }

}
