package br.com.zumbolovsky.fateapp.service

import br.com.zumbolovsky.fateapp.config.error.ErrorArguments.ROLE
import br.com.zumbolovsky.fateapp.domain.DefaultRoles
import br.com.zumbolovsky.fateapp.domain.postgres.Role
import br.com.zumbolovsky.fateapp.domain.postgres.RoleRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service

@Service
class RoleService(
    private var roleRepository: RoleRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(RoleService::class.java)

    fun findRoleByName(role: String): Role =
        DefaultRoles.valueOf(role).let {
            logger.info("Finding role by name: $it...")
            return@let roleRepository.findOne(Example.of(Role(name = it)))
                .orElseThrow { EntityNotFoundException(debugArgs = listOf(ROLE, it)) }
        }

}
