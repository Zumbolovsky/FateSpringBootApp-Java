package br.com.zumbolovsky.fateapp.domain;

import br.com.zumbolovsky.fateapp.service.IllegalArgumentException;

import java.util.List;

import static br.com.zumbolovsky.fateapp.config.error.ErrorArguments.ROLE;

public interface DefaultRoles {
    String USER = "USER";
    String STAFF = "STAFF";
    String ADMIN = "ADMIN";
    List<String> ALL_ROLES = List.of(USER, STAFF, ADMIN);
    static String valueOf(String name) {
        return ALL_ROLES.stream()
                .filter(role -> role.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(List.of(name, ROLE)));
    }
}