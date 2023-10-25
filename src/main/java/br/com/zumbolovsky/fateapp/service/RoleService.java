package br.com.zumbolovsky.fateapp.service;

import br.com.zumbolovsky.fateapp.domain.DefaultRoles;
import br.com.zumbolovsky.fateapp.domain.postgres.Role;
import br.com.zumbolovsky.fateapp.domain.postgres.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.zumbolovsky.fateapp.config.error.ErrorArguments.ROLE;

@Service
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleByName(String role) {
        final String valueOfRole = DefaultRoles.valueOf(role);
        logger.info("Finding role by name: {}...", valueOfRole);
        return roleRepository.findOne(Example.of(new Role(valueOfRole)))
                .orElseThrow(() -> new EntityNotFoundException(List.of(ROLE, valueOfRole)));
    }
}
