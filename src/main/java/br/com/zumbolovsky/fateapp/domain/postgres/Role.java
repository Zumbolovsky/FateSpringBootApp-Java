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

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "ROLE")
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = -2102100778394674030L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id = null;

    @Column(name = "NAME", nullable = false)
    private String name = null;

    @ManyToMany(mappedBy = "roles")
    private List<UserInfo> users = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ROLE_PRIVILEGES",
            joinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "ID")})
    private List<Privilege> privileges = new ArrayList<>();

    public Role(Integer id, String name, List<UserInfo> users, List<Privilege> privileges) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.privileges = privileges;
    }

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public List<String> mapToPrivileges() {
        final List<String> privilegeNames = this.privileges.stream()
                .map(Privilege::getName)
                .collect(Collectors.toList());
        privilegeNames.add(this.name);
        return privilegeNames;
    }

    @Override
    public String toString() {
        return "Role(id=" + id + ", name=" + name + ", users=" + users + ", privileges=" + privileges + ")";
    }
}
