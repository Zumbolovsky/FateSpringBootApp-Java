package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfoRepository
import liquibase.util.MD5Util
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Example
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.persistence.EntityExistsException
import javax.persistence.EntityNotFoundException

@Service
class UserService(private val userInfoRepository: UserInfoRepository): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails =
        findByExample(UserInfo(user = username))

    fun loadByUsernameAndPassword(username: String?, password: String?): UserDetails =
        findByExample(UserInfo(user = username, password = MD5Util.computeMD5(password)))

    @Cacheable("user")
    private fun findByExample(probe: UserInfo) =
        userInfoRepository.findOne(Example.of(probe))
            .orElseThrow { EntityNotFoundException("User info does not exist!") }
            .let { User(it.user, it.password, emptyList()) }

    fun signUp(userInfo: UserInfo) {
        userInfo.password = MD5Util.computeMD5(userInfo.password)
        userInfoRepository.findOne(Example.of(userInfo))
            .ifPresent { throw EntityExistsException("User ${it.user} already signed in!") }
            .apply { userInfoRepository.save(userInfo) }
    }
}
