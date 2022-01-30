package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfoRepository
import org.springframework.data.domain.Example
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userInfoRepository: UserInfoRepository): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val userInfo = userInfoRepository.findOne(Example.of(UserInfo(user = username)))
            .orElseThrow { RuntimeException("User info does not exist!") }
        return User(userInfo.user, userInfo.password, emptyList())
    }
}
