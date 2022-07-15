package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfoRepository
import org.springframework.data.domain.Example
import spock.lang.Specification

class UserServiceTest extends Specification {

    UserInfoRepository userInfoRepository = Mock()
    UserService userService = new UserService(userInfoRepository)

    def "should find user by user info example"() {
        given:
            def probe = new UserInfo()
            def user = "user"
            def pass = "pass"
            userInfoRepository.findOne(Example.of(probe))
                    >> Optional.of(new UserInfo(1, user, "email", pass))

        when:
            def result = userService.findByExample(probe)

        then:
            result.username == user
            result.password == pass
            result.authorities.isEmpty()
    }
}
