package br.com.zumbolovsky.fateapp.domain.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "USER_INFO")
public class UserInfo implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = -914781323092641082L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id = null;

    @Column(name = "USER_NAME", nullable = false)
    private String user = null;

    @Column(name = "EMAIL", nullable = false)
    private String email = null;

    @Column(name = "PASSWORD", nullable = false)
    private String password = null;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles = new ArrayList<>();

    @Override
    public String toString() {
        return "UserInfo(id=" + id + ", user=" + user + ", email=" + email + ", password=***, roles=" + roles + ")";
    }

    public UserInfo(Integer id, String user, String email, String password, List<Role> roles) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserInfo(Integer id, String user, String email, String password) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.password = password;
    }

    public UserInfo(String user, String email, String password, List<Role> roles) {
        this.user = user;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserInfo() {
    }

    public Integer getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(Role::mapToPrivileges)
                .flatMap(this::mapToAuthorities)
                .collect(Collectors.toList());
    }

    private Stream<SimpleGrantedAuthority> mapToAuthorities(List<String> privileges) {
        return privileges.stream().map(SimpleGrantedAuthority::new);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return user;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
