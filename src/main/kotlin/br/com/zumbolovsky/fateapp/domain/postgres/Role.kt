package br.com.zumbolovsky.fateapp.domain.postgres

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.util.stream.Collectors

@Entity
@Table(name = "ROLE")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    var id: Int? = null,

    @Column(name = "NAME", nullable = false)
    var name: String? = null,

    @ManyToMany(mappedBy = "roles")
    var users: Collection<UserInfo>? = emptyList(),

    @ManyToMany
    @JoinTable(
        name = "ROLE_PRIVILEGES",
        joinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")],
        inverseJoinColumns = [JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "ID")])
    var privileges: Collection<Privilege> = emptyList()
) {
    fun mapToPrivileges() : List<String> =
        this.privileges.stream()
            .map { privilege -> privilege.name!! }
            .collect(Collectors.toList())
            .also { it.add(this.name) }
}
