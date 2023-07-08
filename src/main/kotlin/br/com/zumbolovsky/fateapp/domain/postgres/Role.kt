package br.com.zumbolovsky.fateapp.domain.postgres

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

import java.io.Serializable
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "ROLE_PRIVILEGES",
        joinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")],
        inverseJoinColumns = [JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "ID")])
    var privileges: Collection<Privilege> = emptyList()
) : Serializable {
    fun mapToPrivileges() : List<String> =
        this.privileges.stream()
            .map { privilege -> privilege.name!! }
            .collect(Collectors.toList())
            .also { it.add(this.name) }

    override fun toString(): String {
        return "Role(id=$id, name=$name, users=$users, privileges=$privileges)"
    }

    companion object {

        private const val serialVersionUID: Long = -2102100778394674030L
    }
}
