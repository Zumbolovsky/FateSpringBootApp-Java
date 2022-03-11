package br.com.zumbolovsky.fateapp

import br.com.zumbolovsky.fateapp.web.UserVO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity

class UserControllerTest : IntegratedTests() {

    @Test
    fun `Deveria cadastrar um usuario com sucesso`() =
        testRestTemplate.exchange(
            RequestEntity
                .post("http://localhost:${localPort}/fate/users")
                .body(UserVO("test", "test@test.com", "test")),
            Any::class.java).run {
                Assertions.assertEquals(this.statusCode, HttpStatus.OK)
                Assertions.assertNull(this.body)
            }
}