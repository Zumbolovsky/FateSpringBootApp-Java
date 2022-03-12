package br.com.zumbolovsky.fateapp

import br.com.zumbolovsky.fateapp.web.UserVO
import org.junit.Ignore
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriComponentsBuilder

class TestControllerTest: IntegratedTests() {

    @Test
    @Ignore
    fun `should call mongo test endpoint successfully`() =
        testRestTemplate.exchange(
            UriComponentsBuilder
                .fromHttpUrl(createEndpointUrl("/mongo/test"))
                .build().toUri(),
            HttpMethod.POST,
            HttpEntity(UserVO("test", "test@test.com", "test")),
            Any::class.java).run {
            Assertions.assertEquals(this.statusCode, HttpStatus.OK)
            Assertions.assertNull(this.body)
        }
}