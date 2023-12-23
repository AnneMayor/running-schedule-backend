package com.anne.domain.race.dto

import java.time.Instant

data class RaceDto(
    val raceId: Long? = null,
    var name: String?,
    var description: String?,
    var place: String?,
    var day: Instant?,
    var descriptionUrl: String?,
    var host: String?,
    var startOfRegistration: Instant?,
    var endOfRegistration: Instant?,
    var entryFee: Int?,
)
