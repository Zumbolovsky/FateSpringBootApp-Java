package br.com.zumbolovsky.fateapp

import br.com.zumbolovsky.fateapp.web.AuthRequest
import br.com.zumbolovsky.fateapp.web.UserVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.util.MultiValueMapAdapter
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [ContainersInitializer::class])
abstract class EndToEndTests {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @LocalServerPort
    private var localPort: Int = 0

    protected fun signUp(userVO: UserVO): ResponseEntity<Any> =
        testRestTemplate.exchange(
            UriComponentsBuilder
                .fromHttpUrl(createEndpointUrl("/users"))
                .build().toUri(),
            HttpMethod.POST,
            HttpEntity(userVO),
            Any::class.java
        )

    protected fun login(authRequest: AuthRequest): ResponseEntity<String> =
        testRestTemplate.exchange(
            UriComponentsBuilder
                .fromHttpUrl(createEndpointUrl("/login"))
                .build().toUri(),
            HttpMethod.POST,
            HttpEntity(authRequest),
            String::class.java
        )

    protected fun test(token: String): ResponseEntity<Any> =
        testRestTemplate.exchange(
            UriComponentsBuilder
                .fromHttpUrl(createEndpointUrl("/mongo/test"))
                .build().toUri(),
            HttpMethod.POST,
            HttpEntity<Any>(createAuthorizationHeader(token)),
            Any::class.java
        )

    private fun createEndpointUrl(endpoint: String) = "http://localhost:${localPort}/fate${endpoint}"

    private fun createAuthorizationHeader(token: String) =
        MultiValueMapAdapter(buildMap { put(HttpHeaders.AUTHORIZATION, listOf("Bearer $token")) })
}
