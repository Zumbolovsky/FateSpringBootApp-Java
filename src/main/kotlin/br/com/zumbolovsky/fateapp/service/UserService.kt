package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.config.error.ErrorMessages.USER
import br.com.zumbolovsky.fateapp.config.error.ErrorMessages.USER_ALREADY_SIGNED_IN
import br.com.zumbolovsky.fateapp.domain.postgres.Role
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfoRepository
import jakarta.transaction.Transactional
import liquibase.util.MD5Util
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.data.domain.Example
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import java.util.stream.Stream

@Service
class UserService(
    private val userInfoRepository: UserInfoRepository,
    private val messageSourceAccessor: MessageSourceAccessor
    ): UserDetailsService {

    fun findAll(): List<UserInfo> = userInfoRepository.findAll()


    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails =
        findByExample(UserInfo(user = username))

    @Transactional
    fun loadByUsernameAndPassword(username: String?, password: String?): UserDetails =
        findByExample(UserInfo(user = username, password = MD5Util.computeMD5(password)))

    @Cacheable("user")
    protected fun findByExample(probe: UserInfo): User =
        userInfoRepository.findOne(Example.of(probe))
            .orElseThrow {
                probe.user?.let {
                    EntityNotFoundException(debugArgs = listOf(USER, it))
                }
            }
            .let { User(it.user, it.password, getAuthorities(it.roles!!)) }

    private fun getAuthorities(roles: Collection<Role>): List<SimpleGrantedAuthority> =
        roles.stream()
            .map { role -> role.mapToPrivileges() }
            .flatMap { privileges -> mapToAuthorities(privileges) }
            .collect(Collectors.toList())

    private fun mapToAuthorities(privileges: List<String>): Stream<SimpleGrantedAuthority> =
        privileges.stream().map { SimpleGrantedAuthority(it) }

    @Transactional
    fun signUp(userInfo: UserInfo): Unit =
        userInfoRepository.findOne(
            Example.of(
                userInfo.apply { this.password = MD5Util.computeMD5(this.password) }))
            .ifPresent {
                throw EntityExistsException(
                    debugArgs = listOf(USER, messageSourceAccessor.getMessage(USER_ALREADY_SIGNED_IN, arrayOf(it.user!!))))
            }.apply { userInfoRepository.save(userInfo) }
}
