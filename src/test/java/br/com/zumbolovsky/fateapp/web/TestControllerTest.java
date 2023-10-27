package br.com.zumbolovsky.fateapp.web;

import br.com.zumbolovsky.fateapp.service.TestEnum;
import br.com.zumbolovsky.fateapp.service.TestProcessorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TestControllerTest extends EndToEndAutoConfiguration {

    @ParameterizedTest
    @CsvSource({"TEST_A", "TEST_B", "TEST_C",})
    public void shouldCallTestPluginEndpointSuccessfully(String processor) {
        final TestEnum processorEnum = TestEnum.valueOf(processor);
        final ResponseEntity<String> response = testPluginProcessor(processorEnum);
        if (TestEnum.TEST_C == processorEnum) {
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
            Assertions.assertNotNull(response.getBody());
            Assertions.assertTrue(response.getBody().contains("The implementation of %s for type %s was not found!".formatted(TestProcessorService.class.getSimpleName(), processorEnum.name())));
        } else {
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
            Assertions.assertNotNull(response.getBody());
            Assertions.assertTrue(response.getBody().contains("I'm implementation %s".formatted(processor.split("_")[1])));
        }
    }
}
