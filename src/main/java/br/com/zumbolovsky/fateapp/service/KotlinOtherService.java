package br.com.zumbolovsky.fateapp.service;

import org.springframework.stereotype.Service;

@Service
public class KotlinOtherService {

    public Integer calculate(Integer x, Integer y) {
        return x + y;
    }
}