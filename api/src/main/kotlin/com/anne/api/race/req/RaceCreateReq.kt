package com.anne.api.race.req

import java.time.Instant

data class RaceCreateReq(
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
)
