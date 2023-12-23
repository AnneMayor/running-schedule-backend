package com.anne.api.race.res

data class RaceRes(
    val raceId: Long,
    var id: Long?,
    var name: String?,
    var description: String?,
    var place: String?,
    var day: String?,
    var descriptionUrl: String?,
    var host: String?,
    var phoneNumber: String?,
    var startOfRegistration: String?,
    var endOfRegistration: String?,
    var entryFee: Int?,
)
