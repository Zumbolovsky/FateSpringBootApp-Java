package br.com.zumbolovsky.fateapp.domain.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "PRIVILEGE")
public class Privilege implements Serializable {

    @Serial
    private static final long serialVersionUID = -286442425190624268L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id = null;

    @Column(name = "NAME", nullable = false)
    private String name = null;

    @ManyToMany(mappedBy = "privileges")
    private List<Role> roles = new ArrayList<>();

    @Override
    public String toString() {
        return "Privilege(id=" + id + ", name=" + name + ", roles=" + roles + ")";
    }

    public Privilege(Integer id, String name, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }

    public Privilege() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
