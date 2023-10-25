package br.com.zumbolovsky.fateapp.web;

import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

public class AuthRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 6134437725178706338L;

    @NotNull(message = "{user.name} {not.null}")
    private String user;
    @NotNull(message = "{user.password} {not.null}")
    private String password;

    public AuthRequest(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public AuthRequest() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
