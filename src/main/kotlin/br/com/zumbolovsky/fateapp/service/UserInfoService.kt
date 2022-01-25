package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserInfoService(
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val userInfoRepository: UserInfoRepository) {

    fun singUp(userInfo: UserInfo) {
        userInfo.password = bCryptPasswordEncoder.encode(userInfo.password)
        userInfoRepository.save(userInfo)
    }
}