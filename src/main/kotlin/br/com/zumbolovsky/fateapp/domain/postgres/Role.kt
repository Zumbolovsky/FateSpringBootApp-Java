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
@Table(name = "ROLE")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    var id: Long? = null,

    @Column(name = "NAME", nullable = false)
    var name: String? = null,

    @ManyToMany(mappedBy = "roles")
    var users: Collection<UserInfo>,

    @ManyToMany
    @JoinTable(
        name = "ROLES_PRIVILEGES",
        joinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")],
        inverseJoinColumns = [JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "ID")]
    )
    var privileges: Collection<Privilege>)
