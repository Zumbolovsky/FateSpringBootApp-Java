package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.config.error.ErrorMessages.ADMIN_ROLE
import br.com.zumbolovsky.fateapp.config.error.ErrorMessages.USER_ROLE
import br.com.zumbolovsky.fateapp.domain.DefaultRoles
import br.com.zumbolovsky.fateapp.domain.postgres.Role
import br.com.zumbolovsky.fateapp.domain.postgres.RoleRepository
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service

@Service
class RoleService(
    private var roleRepository: RoleRepository) {

    fun findAdminRole(): Role =
        roleRepository.findOne(
            Example.of(
                Role(name = DefaultRoles.ADMIN.name)))
            .orElseThrow { EntityNotFoundException(debugArgs = listOf(ADMIN_ROLE)) }

    fun findUserRole(): Role =
        roleRepository.findOne(
            Example.of(
                Role(name = DefaultRoles.USER.name)))
            .orElseThrow { EntityNotFoundException(debugArgs = listOf(USER_ROLE)) }
}
