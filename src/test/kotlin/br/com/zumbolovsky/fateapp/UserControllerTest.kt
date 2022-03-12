package br.com.zumbolovsky.fateapp

import br.com.zumbolovsky.fateapp.web.AuthRequest
import br.com.zumbolovsky.fateapp.web.UserVO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriComponentsBuilder

class UserControllerTest : IntegratedTests() {

    @Test
    fun `should call user sign up endpoint successfully`() =
        testRestTemplate.exchange(
            UriComponentsBuilder
                .fromHttpUrl(createEndpointUrl("/users"))
                .build().toUri(),
            HttpMethod.POST,
            HttpEntity(UserVO("test", "test@test.com", "test")),
            Any::class.java).run {
                Assertions.assertEquals(this.statusCode, HttpStatus.OK)
                Assertions.assertNull(this.body)
            }

    @Test
    fun `should call user login endpoint successfully`() =
        testRestTemplate.exchange(
            UriComponentsBuilder
                .fromHttpUrl(createEndpointUrl("/login"))
                .build().toUri(),
            HttpMethod.POST,
            HttpEntity(AuthRequest("test", "test")),
            String::class.java)
            .run {
                Assertions.assertEquals(this.statusCode, HttpStatus.OK)
                val token = this.headers["Authorization"]
                Assertions.assertNotNull(token)
            }
}