package br.com.zumbolovsky.fateapp.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpHeaders
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
class AuthorizationTestController {

    private val getMessage: (String, String) -> String = { name, type  : String -> "You have $name $type" }

    @GetMapping("/authorization/privilege/read")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    @Operation(summary = "Testing read privilege")
    fun testReadPrivilege(): String = getMessage("read", "privileges")

    @GetMapping("/authorization/privilege/write")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @Operation(summary = "Testing write privilege")
    fun testWritePrivilege(): String = getMessage("write", "privileges")

    @GetMapping("/authorization/privilege/delete")
    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE')")
    @Operation(summary = "Testing delete privilege")
    fun testDeletePrivilege(): String = getMessage("delete", "privileges")

    @GetMapping("/authorization/role/user")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Testing user role")
    fun testUserRole(): String = getMessage("user", "role")

    @GetMapping("/authorization/role/staff")
    @PreAuthorize("hasAuthority('STAFF')")
    @Operation(summary = "Testing staff privilege")
    fun testStaffRole(): String = getMessage("staff", "role")

    @GetMapping("/authorization/role/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Testing admin role")
    fun testAdminRole(): String = getMessage("admin", "role")

}