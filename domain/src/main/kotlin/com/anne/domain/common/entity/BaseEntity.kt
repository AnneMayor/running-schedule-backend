package com.anne.domain.common.entity

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @CreatedBy
    var createdBy: String? = null
        protected set

    @CreatedDate
    var createdAt: Instant = Instant.now()
        protected set

    @LastModifiedBy
    var modifiedBy: String? = null
        protected set

    @LastModifiedDate
    var modifiedAt: Instant = Instant.now()
        protected set
}