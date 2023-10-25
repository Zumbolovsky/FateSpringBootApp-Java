package br.com.zumbolovsky.fateapp.service;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
public class KotlinAppServiceTest {

    @Mock
    private KotlinOtherService kotlinOtherService;

    @Mock
    private PluginRegistry<TestProcessorService, TestEnum> testProcessorPluginRegistry;

    @InjectMocks
    private KotlinAppService kotlinAppService;

    @Test
    public void calculateAllShouldThrowRuntimeExceptionForInvalidValues() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            kotlinAppService.calculateAll(null);
            kotlinAppService.calculateAll(Arrays.array());
            kotlinAppService.calculateAll(Arrays.array(0, 0, 0));
        });
    }

    @Test
    public void calculateAllShouldAssertValues() {
        doReturn(3).when(kotlinOtherService).calculate(1, 2);
        var valores = Arrays.array(1, 1);
        Assertions.assertEquals(6, kotlinAppService.calculateAll(valores));
    }
}