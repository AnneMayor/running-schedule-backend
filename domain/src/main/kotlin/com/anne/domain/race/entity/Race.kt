package com.anne.domain.race.entity

import com.anne.domain.common.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Table
import org.springframework.data.annotation.Id
import java.time.Instant

@Entity
@Table
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
    val raceId: Long? = null
}
