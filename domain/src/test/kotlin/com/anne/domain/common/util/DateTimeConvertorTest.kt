package com.anne.domain.common.util

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.ZoneId

class DateTimeConvertorTest : ShouldSpec({
    context("DateTimeConvertor") {
        should("convertStringToLocalDate") {
            // given
            val dateString = "2023-12-25"
            val actual = DateTimeConvertor.convertStringToLocalDate(dateString)
            val expected = LocalDate.of(2023, 12, 25)

            actual shouldBe expected
        }

        should("convertLocalDateToInstant") {
            // given
            val localDate = LocalDate.of(2023, 12, 25)
            val zoneId = ZoneId.systemDefault()

            val actual = DateTimeConvertor.convertLocalDateToInstant(localDate, zoneId)
            val expected = localDate.atStartOfDay(zoneId).toInstant()

            actual shouldBe expected
        }
    }
})
