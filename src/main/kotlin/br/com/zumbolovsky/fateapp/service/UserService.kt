package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.config.error.ErrorArguments.USER
import br.com.zumbolovsky.fateapp.config.error.ErrorDebugMessages.USER_ALREADY_SIGNED_IN
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfoRepository
import jakarta.transaction.Transactional
import liquibase.util.MD5Util
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.data.domain.Example
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userInfoRepository: UserInfoRepository,
    private val messageSourceAccessor: MessageSourceAccessor
    ): UserDetailsService {

    private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun findAllByRole(role: String): List<UserInfo> =
        role.let {
            logger.info("Finding all users by role name: $it...")
            return@let userInfoRepository.findAllByRole(it)
        }


    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails =
        kotlin.run {
            logger.info("Finding user info by user $username...")
            return@run findByUserAndPassword(username!!)
        }

    @Cacheable("user")
    private fun findByUserAndPassword(username: String, password: String? = null): UserDetails =
        (if (password != null) userInfoRepository.findOneByUserAndPassword(username, MD5Util.computeMD5(password))
        else userInfoRepository.findOneByUser(username))
            .orElseThrow { EntityNotFoundException(debugArgs = listOf(USER, username)) }

    @Transactional
    fun signUp(userInfo: UserInfo): Unit =
        userInfoRepository.findOne(Example.of(userInfo.apply { this.password = MD5Util.computeMD5(this.password) }))
            .ifPresent {
                throw EntityExistsException(
                    debugArgs = listOf(USER, messageSourceAccessor.getMessage(USER_ALREADY_SIGNED_IN, arrayOf(it.user!!)))
                )
            }.apply { userInfoRepository.save(userInfo) }
}
