package br.com.zumbolovsky.fateapp.web

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

class UserControllerTest : EndToEndAutoConfiguration() {

    @ParameterizedTest
    @CsvSource("test, test")
    fun `should call user sign up endpoint successfully`(user: String, password: String) =
        signUp(UserVO(user, "test@test.com", password))
            .run {
                Assertions.assertEquals(this.statusCode, HttpStatus.OK)
                Assertions.assertNull(this.body)
            }

    @ParameterizedTest
    @CsvSource("andrew, test")
    fun `should call user login endpoint successfully`(user: String, password: String) {
        signUp(UserVO(user, "test@test.com", password))
        login(AuthRequest(user, password)).also {
            Assertions.assertEquals(it.statusCode, HttpStatus.OK)
            Assertions.assertNotNull(it.headers[HttpHeaders.AUTHORIZATION])
        }
    }
}