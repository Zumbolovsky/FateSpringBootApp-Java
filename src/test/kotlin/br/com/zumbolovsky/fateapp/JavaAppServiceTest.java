package br.com.zumbolovsky.fateapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class JavaAppServiceTest {

    @Mock
    private JavaOtherService javaOtherService;

    @InjectMocks
    private JavaAppService javaAppService;

    @Test
    public void calculateAllShouldThrowRuntimeExceptionForInvalidValues() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            javaAppService.calculateAll(null);
            javaAppService.calculateAll(new Integer[]{});
            javaAppService.calculateAll(new Integer[]{0, 0, 0});
        });
    }

    @Test
    public void calculateAllAssertValues() {
        Mockito.doReturn(3).when(javaOtherService).calculate(1, 2);
        final Integer[] valores = new Integer[]{1, 1};
        Assertions.assertEquals(6, javaAppService.calculateAll(valores));
    }
}