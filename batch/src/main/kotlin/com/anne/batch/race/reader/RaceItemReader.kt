package com.anne.batch.race.reader

import com.anne.batch.race.item.RaceItem
import com.anne.domain.common.util.CrawlerUtil
import com.anne.domain.common.util.UrlCreator
import org.springframework.batch.item.ItemReader
import java.time.Instant
import java.time.LocalDateTime


data class HostInfo(
    val phoneNumber: String,
    val hostName: String,
    val descriptionUrl: String? = null,
)

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

        val place = CrawlerUtil.selectElements(raceDocument, "td[width=19%]").select("div[align=center]")
            .toList()
            .filter { it.text() != "장소" }
            .mapNotNull { it.text() }

        val hostInfoElements =
            CrawlerUtil.selectElements(raceDocument, "td[width=30%]").select("div[align=right][valign=bottom]")
        val hostInfoList: List<HostInfo> =
            hostInfoElements.map {
                val hostNameAndPhoneNumber = it.text().split("☎")
                val descriptionUrl = it.selectFirst("a")?.attributes()?.first()?.value
                HostInfo(
                    hostName = hostNameAndPhoneNumber[0],
                    phoneNumber = hostNameAndPhoneNumber[1],
                    descriptionUrl = descriptionUrl
                )
            }


        val entryFee = null // TODO("등록비 정보는 다른 곳에서 Crawling 해오는걸로!")
        val startOfRegistration = null // TODO("대회 신청일자 정보는 다른 곳에서 Crawling 해오는걸로!")
        val endOfRegistration = null // TODO("신청마감일 정보는 다른 곳에서 Crawling 해오는걸로!")

        val crawlingData = mutableMapOf<String, String?>()

        for (index in raceDates.indices) {
            val raceDay = "${LocalDateTime.now().year}/$raceDates[index]"
            val raceName = raceNames[index]
            crawlingData["day"] = raceDay
            crawlingData["name"] = raceName
            crawlingData["place"] = place[index]
            crawlingData["phoneNumber"] = hostInfoList[index].phoneNumber
            crawlingData["host"] = hostInfoList[index].hostName
            crawlingData["descriptionUrl"] = hostInfoList[index].descriptionUrl
        }

        return RaceItem(
            name = crawlingData["name"],
            description = null,
            place = crawlingData["place"],
            day = Instant.parse(crawlingData["day"]),
            descriptionUrl = crawlingData["descriptionUrl"],
            host = crawlingData["host"],
            phoneNumber = crawlingData["phoneNumber"],
            entryFee = entryFee,
            startOfRegistration = startOfRegistration,
            endOfRegistration = endOfRegistration,
        )
    }
}
