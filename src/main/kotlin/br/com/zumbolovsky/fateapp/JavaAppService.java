package br.com.zumbolovsky.fateapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class JavaAppService {

    @Autowired
    private JavaOtherService javaOtherService;

    public Integer calculateAll(final Integer[] valores) {
        if (valores == null || valores.length == 0) {
            throw new RuntimeException("Valores inválidos");
        }
        var sumOf = Arrays.stream(valores)
                .map(valor -> javaOtherService.calculate(valor, 2))
                .reduce(0, Integer::sum);
        if (sumOf == 0) {
            throw new RuntimeException("Erro de cálculo");
        }
        return sumOf;
    }
}
