package com.anne.domain.common.entity

import jakarta.persistence.*
import org.hibernate.envers.RevisionEntity
import org.hibernate.envers.RevisionNumber
import org.hibernate.envers.RevisionTimestamp


@Entity
@RevisionEntity
@Table(name = "revinfo")
class RevInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "rev")
    val rev: Long? = null

    @RevisionTimestamp
    @Column(name = "rev_tstmp")
    private val revtstmp: Long? = null
}