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
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import java.io.Serializable
import java.util.stream.Collectors
import java.util.stream.Stream

@Entity
@Table(name = "USER_INFO")
data class UserInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    var id: Int? = null,

    @Column(name = "USER_NAME", nullable = false)
    var user: String? = null,

    @Column(name = "EMAIL", nullable = false)
    var email: String? = null,

    @Column(name = "PASSWORD", nullable = false)
    @JvmField
    var password: String? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "USER_ROLES",
        joinColumns = [JoinColumn(name = "USER_ID", referencedColumnName = "ID")],
        inverseJoinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")])
    var roles: Collection<Role>? = emptyList()
) : UserDetails, Serializable {
    override fun toString(): String {
        return "UserInfo(id=$id, user=$user, email=$email, password=***, roles=$roles)"
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles!!.stream()
            .map { role -> role.mapToPrivileges() }
            .flatMap { privileges -> mapToAuthorities(privileges) }
            .collect(Collectors.toList())

    private fun mapToAuthorities(privileges: List<String>): Stream<SimpleGrantedAuthority> =
        privileges.stream().map { SimpleGrantedAuthority(it) }

    override fun getPassword(): String = password!!

    override fun getUsername(): String = user!!

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    companion object {

        private const val serialVersionUID: Long = -914781323092641082L
    }

}
