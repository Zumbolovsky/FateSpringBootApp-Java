package br.com.zumbolovsky.fateapp.web;

import br.com.zumbolovsky.fateapp.domain.postgres.Role;
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class UserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7964835547027230261L;

    @NotBlank(message = "{user.name} {not.blank}")
    private String user;
    @NotBlank(message = "{user.email} {not.blank}")
    @Email(message = "{user.email} {email.validation}")
    private String email;
    @NotBlank(message = "{user.password} {not.blank}")
    private String password;

    public UserVO(String user, String email, String password) {
        this.user = user;
        this.email = email;
        this.password = password;
    }

    public UserVO() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInfo toUserInfo(Role role) {
        return new UserInfo(
                user,
                email,
                password,
                List.of(role)
        );
    }

    public static UserVO fromUserInfo(UserInfo userInfo) {
        return new UserVO(
                userInfo.getUser(),
                userInfo.getEmail(),
                null
        );
    }

}
