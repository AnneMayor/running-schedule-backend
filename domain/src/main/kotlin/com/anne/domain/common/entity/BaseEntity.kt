package com.anne.domain.common.entity

import jakarta.persistence.Entity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

@Entity
open class BaseEntity {
    @set:CreatedDate
    var createdAt: Instant? = null

    @set:LastModifiedDate
    var modifiedAt: Instant? = null
}
