package com.anne.batch.race.item

import java.time.Instant

data class RaceItem(
    var name: String?,
    var description: String?,
    var place: String?,
    var day: Instant?,
    var descriptionUrl: String?,
    var host: String?,
    var phoneNumber: String?,
    var entryFee: Int?,
    var startOfRegistration: Instant?,
    var endOfRegistration: Instant?,
)
