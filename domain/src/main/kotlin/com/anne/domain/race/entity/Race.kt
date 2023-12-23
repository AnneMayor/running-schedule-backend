package com.anne.domain.race.entity

import com.anne.domain.common.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.envers.AuditOverride
import org.hibernate.envers.Audited
import java.time.Instant

@Entity
@Table
@Audited
@AuditOverride(forClass = BaseEntity::class)
class Race(
    var name: String?,
    var description: String?,
    var place: String?,
    var day: Instant?,
    var descriptionUrl: String?,
    var host: String?,
    var phoneNumber: String?,
    var startOfRegistration: Instant?,
    var endOfRegistration: Instant?,
    var entryFee: Int?,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val raceId: Long = 0L
}
