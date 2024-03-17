package com.anne.domain.common.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

object DateTimeConvertor {
    fun convertStringToLocalDate(date: String): LocalDate {
        return LocalDate.parse(date)
    }

    fun convertLocalDateToInstant(date: LocalDate, zoneId: ZoneId): Instant {
        return date.atStartOfDay(zoneId).toInstant()
    }
}
