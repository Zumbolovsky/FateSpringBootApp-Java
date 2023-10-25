package br.com.zumbolovsky.fateapp.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class UserControllerTest extends EndToEndAutoConfiguration {

    @ParameterizedTest
    @CsvSource({
            "admin, admin, test1, test1, test1@test.com, USER",
            "staff, staff, test2, test2, test2@test.com, STAFF",
            "staff, staff, test3, test3, test3@test.com, ADMIN",
    })
    public void shouldCallUserSignUpEndpointSuccessfully(String loginUser, String loginPassword, String signUpUser, String signUpPassword, String signUpEmail, String signUpRole) {
        final ResponseEntity<String> loggedInResponse = login(new AuthRequest(loginUser, loginPassword));
        final List<String> authHeader = loggedInResponse.getHeaders().get(HttpHeaders.AUTHORIZATION);
        Assertions.assertNotNull(authHeader);
        Assertions.assertEquals(authHeader.size(), 1);
        final ResponseEntity<Object> response = signUp(new UserVO(signUpUser, signUpEmail, signUpPassword), signUpRole, authHeader.get(0));
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNull(response.getBody());
    }

    @ParameterizedTest
    @CsvSource({"user, user", "staff, staff", "admin, admin"})
    public void shouldCallUserLoginEndpointSuccessfully(String user, String password) {
        final ResponseEntity<String> response = login(new AuthRequest(user, password));
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getHeaders().get(HttpHeaders.AUTHORIZATION));
    }
}