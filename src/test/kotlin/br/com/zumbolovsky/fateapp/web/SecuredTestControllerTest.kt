package br.com.zumbolovsky.fateapp.web

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import kotlin.test.assertNotNull

class SecuredTestControllerTest: EndToEndAutoConfiguration() {

    @ParameterizedTest
    @CsvSource("user, test")
    fun `should call mongo test endpoint successfully`(user: String, password: String) {
        signUp(UserVO(user, "test@test.com", password))
        login(AuthRequest(user, password))
            .also {
                val tokenList = it.headers[HttpHeaders.AUTHORIZATION]
                assertNotNull(tokenList)
                test(tokenList[0])
                    .run {
                        Assertions.assertEquals(this.statusCode, HttpStatus.OK)
                        Assertions.assertNotNull(this.body)
                }
            }
    }
}