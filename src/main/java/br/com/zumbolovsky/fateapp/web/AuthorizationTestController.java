package br.com.zumbolovsky.fateapp.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.BiFunction;

@RestController
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AuthorizationTestController {

    private final BiFunction<String, String, String> getMessage = (String name, String type) -> "You have " + name + " " + type;

    @GetMapping("/authorization/privilege/read")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    @Operation(summary = "Testing read privilege")
    public String testReadPrivilege() {
        return getMessage.apply("read", "privileges");
    }

    @GetMapping("/authorization/privilege/write")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @Operation(summary = "Testing write privilege")
    public String testWritePrivilege() {
        return getMessage.apply("write", "privileges");
    }

    @GetMapping("/authorization/privilege/delete")
    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE')")
    @Operation(summary = "Testing delete privilege")
    public String testDeletePrivilege() {
        return getMessage.apply("delete", "privileges");
    }

    @GetMapping("/authorization/role/user")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Testing user role")
    public String testUserRole() {
        return getMessage.apply("user", "role");
    }

    @GetMapping("/authorization/role/staff")
    @PreAuthorize("hasAuthority('STAFF')")
    @Operation(summary = "Testing staff privilege")
    public String testStaffRole() {
        return getMessage.apply("staff", "role");
    }

    @GetMapping("/authorization/role/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Testing admin role")
    public String testAdminRole() {
        return getMessage.apply("admin", "role");
    }

}