package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfoRepository
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.data.domain.Example
import spock.lang.Specification

class UserServiceTest extends Specification {

    UserInfoRepository userInfoRepository = Mock()
    MessageSourceAccessor messageSourceAccessor = Mock()
    UserService userService = new UserService(userInfoRepository, messageSourceAccessor)

    def "should find user by user info example"() {
        given:
        def user = "user"
        def pass = "pass"

        when:
        def result = userService.findByUserAndPassword(user)

        then:
        1 * userInfoRepository.findOneByUser(user) >> Optional.of(new UserInfo(1, user, "email", pass))
        result.username == user
        result.password == pass
        result.authorities.isEmpty()
    }
}
