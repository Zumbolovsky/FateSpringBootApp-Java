package br.com.zumbolovsky.fateapp.domain.postgres

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Email

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
    var password: String? = null)
