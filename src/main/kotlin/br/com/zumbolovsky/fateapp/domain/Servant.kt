package br.com.zumbolovsky.fateapp.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "PERSONAGENS")
data class Servant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    var id: Int? = null,

    @Column(name = "NOME", nullable = false)
    var name: String? = null,

    @Column(name = "RARIDADE", nullable = false)
    @Enumerated(EnumType.STRING)
    var rarity: Rarity? = null)
