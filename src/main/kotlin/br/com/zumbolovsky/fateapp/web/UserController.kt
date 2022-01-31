package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo
import br.com.zumbolovsky.fateapp.service.UserInfoService
import io.swagger.v3.oas.annotations.Operation
import org.modelmapper.ModelMapper
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class UserController(
    private val userInfoService: UserInfoService,
    private val modelMapper: ModelMapper) {

    @PostMapping("/users")
    @Operation(summary = "Sign up a user with the information provided.")
    fun signUp(@RequestBody @Valid userVO: UserVO) {
        val userInfo = modelMapper.map(userVO, UserInfo::class.java)
        userInfoService.singUp(userInfo)
    }

}
