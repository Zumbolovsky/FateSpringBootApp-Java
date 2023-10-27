package br.com.zumbolovsky.fateapp.web;

import br.com.zumbolovsky.fateapp.ContainersInitializer;
import br.com.zumbolovsky.fateapp.service.TestEnum;
import org.assertj.core.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {ContainersInitializer.class})
abstract class EndToEndAutoConfiguration {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private final Integer localPort = 0;

    protected ResponseEntity<Object> signUp(UserVO userVO, String role, String token) {
        return testRestTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(createEndpointUrl("/users/%s".formatted(role)))
                        .build().toUri(),
                HttpMethod.POST,
                new HttpEntity<>(userVO, createAuthorizationHeader(token)),
                Object.class
        );
    }

    protected ResponseEntity<String> login(AuthRequest authRequest) {
        return testRestTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(createEndpointUrl("/login"))
                        .build().toUri(),
                HttpMethod.POST,
                new HttpEntity<>(authRequest),
                String.class
        );
    }

    protected ResponseEntity<String> testPluginProcessor(TestEnum testEnum) {
        return testRestTemplate.getForEntity(
                UriComponentsBuilder
                        .fromHttpUrl(createEndpointUrl("/plugin/test/%s".formatted(testEnum.name())))
                        .build().toUri(),
                String.class
        );
    }

    protected ResponseEntity<Object> test(String token) {
        return testRestTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(createEndpointUrl("/secured/mongo/test"))
                        .build().toUri(),
                HttpMethod.POST,
                new HttpEntity<>(createAuthorizationHeader(token)),
                Object.class
        );
    }

    private String createEndpointUrl(String endpoint) {
        return "http://localhost:%d/fate%s".formatted(localPort, endpoint);
    }

    private MultiValueMap<String, String> createAuthorizationHeader(String token) {
        return new MultiValueMapAdapter<>(Maps.newHashMap(HttpHeaders.AUTHORIZATION, List.of("Bearer %s".formatted(token))));
    }
}
