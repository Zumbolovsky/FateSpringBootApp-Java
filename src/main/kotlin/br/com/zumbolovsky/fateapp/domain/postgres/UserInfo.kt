package br.com.zumbolovsky.fateapp.domain.postgres

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

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
    var password: String? = null,

    @ManyToMany
    @JoinTable(
        name = "USERS_ROLES",
        joinColumns = [JoinColumn(name = "USER_ID", referencedColumnName = "ID")],
        inverseJoinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")])
    var roles: Collection<Role>)
