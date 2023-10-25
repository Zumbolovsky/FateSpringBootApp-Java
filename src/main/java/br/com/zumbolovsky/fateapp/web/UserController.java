package br.com.zumbolovsky.fateapp.web;

import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo;
import br.com.zumbolovsky.fateapp.service.RoleService;
import br.com.zumbolovsky.fateapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users/{role}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    @Operation(summary = "Get all registered users by role.")
    public List<UserVO> findAllByRole(@PathVariable("role") String role) {
        return userService.findAllByRole(role).stream().map(UserVO::fromUserInfo).toList();
    }

    @PostMapping("/users/{role}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @Operation(summary = "Sign up a user with specific role and registration information.")
    public void signUpByRole(@PathVariable("role") String role, @RequestBody @Validated UserVO userVO) {
        final UserInfo userInfo = userVO.toUserInfo(roleService.findRoleByName(role));
        userService.signUp(userInfo);
    }

}
