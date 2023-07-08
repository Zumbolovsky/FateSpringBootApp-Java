package br.com.zumbolovsky.fateapp.domain.postgres

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

import java.io.Serializable


@Entity
@Table(name = "PRIVILEGE")
data class Privilege(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    var id: Int? = null,

    @Column(name = "NAME", nullable = false)
    var name: String? = null,

    @ManyToMany(mappedBy = "privileges")
    var roles: Collection<Role>? = emptyList()
) : Serializable {
    override fun toString(): String {
        return "Privilege(id=$id, name=$name, roles=$roles)"
    }

    companion object {

        private const val serialVersionUID: Long = -286442425190624268L
    }
}
