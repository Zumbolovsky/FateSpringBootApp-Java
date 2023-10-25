package br.com.zumbolovsky.fateapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class KotlinAppService {

    private final KotlinOtherService kotlinOtherService;
    private final PluginRegistry<TestProcessorService, TestEnum> testProcessorPluginRegistry;

    private static final Logger logger = LoggerFactory.getLogger(KotlinAppService.class);

    public KotlinAppService(KotlinOtherService kotlinOtherService, PluginRegistry<TestProcessorService, TestEnum> testProcessorPluginRegistry) {
        this.kotlinOtherService = kotlinOtherService;
        this.testProcessorPluginRegistry = testProcessorPluginRegistry;
    }

    public Integer calculateAll(Integer... valores) {
        if (valores == null || valores.length == 0) {
            throw new RuntimeException("Valores inválidos");
        }
        Integer sumOf = Stream.of(valores).map(valor -> kotlinOtherService.calculate(valor, 2)).reduce(Integer::sum).orElse(0);
        if (sumOf == 0) {
            throw new RuntimeException("Erro de cálculo");
        }
        return sumOf;
    }

    public String testPluginRegistry(TestEnum processor) {
        final String process = testProcessorPluginRegistry.getPluginFor(processor)
                .orElseThrow(() -> new ImplementationNotFoundException(List.of(TestProcessorService.class.getSimpleName(), processor.name())))
                .process();
        logger.info(process);
        return process;
    }
}