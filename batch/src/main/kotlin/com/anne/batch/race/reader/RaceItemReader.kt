package com.anne.batch.race.reader

import com.anne.batch.race.item.RaceItem
import com.anne.domain.common.util.CrawlerUtil
import com.anne.domain.common.util.UrlCreator
import org.springframework.batch.item.ItemReader
import java.time.Instant
import java.time.LocalDateTime


class RaceItemReader() : ItemReader<RaceItem?> {
    override fun read(): RaceItem? {
        val raceUrl = UrlCreator.makeCurrentDateRaceUrl(Instant.now().epochSecond)

        val raceDocument = CrawlerUtil.getDocument(raceUrl)

        val raceDates =
            CrawlerUtil.selectElements(raceDocument, "font[size=4][face=Arial, Helvetica, sans-serif]").text()
                .split(" ")
                .filter {
                    it.contains("\\d{2}/\\d{2}".toRegex())
                }

        val raceNamesWithDistances =
            CrawlerUtil.selectElements(raceDocument, "font[size=3][face=Arial, Helvetica, sans-serif]").toList()
                .map {
                    it.text()
                }
        val raceNames = raceNamesWithDistances
            .map {
                it.replace(
                    CrawlerUtil.REGEX_RACE_NAME.toRegex(), ""

                )
            }

        val crawlingData = mutableMapOf<String, String?>()

        for (index in raceDates.indices) {
            val raceDay = "${LocalDateTime.now().year}/$raceDates[index]"
            val raceName = raceNames[index]
            crawlingData["day"] = raceDay
            crawlingData["name"] = raceName
            crawlingData["place"] = null
            crawlingData["description"] = null
            crawlingData["phoneNumber"] = null
            crawlingData["host"] = null
        }

        return RaceItem(
            name = crawlingData["name"],
            description = crawlingData["description"],
            place = crawlingData["place"],
            day = crawlingData["day"],
            descriptionUrl = null,
            host = crawlingData["host"],
            phoneNumber = crawlingData["phoneNumber"],
        )
    }
}
