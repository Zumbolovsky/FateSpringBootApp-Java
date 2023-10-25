package br.com.zumbolovsky.fateapp.service;

import org.springframework.plugin.core.Plugin;

public interface TestProcessorService extends Plugin<TestEnum> {
    String process();
}