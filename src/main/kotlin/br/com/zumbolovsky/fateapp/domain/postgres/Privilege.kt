package br.com.zumbolovsky.fateapp.domain.postgres

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "PRIVILEGE")
data class Privilege(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    var id: Long? = null,

    @Column(name = "NAME", nullable = false)
    var name: String? = null,

    @ManyToMany(mappedBy = "privileges")
    var roles: Collection<Role>)
