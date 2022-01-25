package br.com.zumbolovsky.fateapp.web

import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo
import br.com.zumbolovsky.fateapp.service.UserInfoService
import br.com.zumbolovsky.fateapp.service.UserService
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userInfoService: UserInfoService,
    private val modelMapper: ModelMapper) {

    @PostMapping("/users")
    fun signUp(@RequestBody userVO: UserVO) {
        val userInfo = modelMapper.map(userVO, UserInfo::class.java)
        userInfoService.singUp(userInfo)
    }

}
