package com.anne.batch.race.reader

import com.anne.domain.common.util.CrawlerUtil
import com.anne.domain.common.util.UrlCreator
import com.anne.domain.race.dto.RaceDto
import com.anne.domain.race.service.RaceDomainService
import org.springframework.batch.item.ItemReader
import java.time.Instant
import java.time.LocalDateTime


class RaceItemReader(
    private val raceDomainService: RaceDomainService,
) : ItemReader<RaceDto?> {
    override fun read(): RaceDto? {
        val raceUrl = UrlCreator.makeCurrentDateRaceUrl(Instant.now().epochSecond)

        val raceDocument = CrawlerUtil.getDocument(raceUrl)

        val raceDates =
            CrawlerUtil.selectElements(raceDocument, "font[size=4][face=Arial, Helvetica, sans-serif]").text()
                .split(" ")
                .filter {
                    it.contains("\\d{2}/\\d{2}".toRegex())
                }

        for (raceDate in raceDates) {
            val raceDay = LocalDateTime.now().year.toString() + "/" + raceDate
            println(raceDate)
        }
        TODO("파싱한 정보를 RaceDTO 데이터 형태로 변환한다.")
    }
}
