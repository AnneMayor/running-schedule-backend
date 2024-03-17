package com.anne.batch.race.reader

import com.anne.batch.race.item.RaceItem
import com.anne.domain.common.util.CrawlerUtil
import com.anne.domain.common.util.DateTimeConvertor
import com.anne.domain.common.util.UrlCreator
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.batch.item.ItemReader
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId


data class HostInfo(
    val phoneNumber: String,
    val hostName: String,
    val descriptionUrl: String? = null,
)

class RaceItemReader : ItemReader<RaceItem?> {
    override fun read(): RaceItem? {
        val raceUrl = UrlCreator.makeCurrentDateRaceUrl(Instant.now().epochSecond)

        val raceDocument = CrawlerUtil.getDocument(raceUrl)

        val raceDates = extractRaceDate(raceDocument)

        val raceNamesWithDistances = extractRaceNameWithDistance(raceDocument)

        val raceNames = raceNamesWithDistances
            ?.map {
                it.replace(
                    CrawlerUtil.REGEX_RACE_NAME.toRegex(), ""
                )
            }

        val place = extractPlace(raceDocument)

        val hostInfoElements = getHostInfoElement(raceDocument)

        val hostInfoList: List<HostInfo> = extractHostInfo(hostInfoElements)

        val entryFee = null // TODO("등록비 정보는 다른 곳에서 Crawling 해오는걸로!")
        val startOfRegistration = null // TODO("대회 신청일자 정보는 다른 곳에서 Crawling 해오는걸로!")
        val endOfRegistration = null // TODO("신청마감일 정보는 다른 곳에서 Crawling 해오는걸로!")

        val crawlingData = mutableMapOf<String, Any?>()

        raceDates?.let {
            for (index in it.indices) {
                val raceDay = DateTimeConvertor.convertStringToLocalDate(
                    "${LocalDateTime.now().year}-${
                        it[index].replace(
                            "/",
                            "-"
                        )
                    }"
                )
                val raceName = raceNames?.get(index)
                crawlingData["day"] = raceDay
                crawlingData["name"] = raceName
                crawlingData["place"] = place?.get(index)
                crawlingData["phoneNumber"] = hostInfoList[index].phoneNumber
                crawlingData["host"] = hostInfoList[index].hostName
                crawlingData["descriptionUrl"] = hostInfoList[index].descriptionUrl
            }
        }

        return RaceItem(
            name = crawlingData["name"].toString(),
            description = null,
            place = crawlingData["place"].toString(),
            day = DateTimeConvertor.convertLocalDateToInstant(
                crawlingData["day"] as LocalDate,
                ZoneId.systemDefault()
            ),
            descriptionUrl = crawlingData["descriptionUrl"].toString(),
            host = crawlingData["host"].toString(),
            phoneNumber = crawlingData["phoneNumber"].toString(),
            entryFee = entryFee,
            startOfRegistration = startOfRegistration,
            endOfRegistration = endOfRegistration,
        )
    }

    private fun extractRaceDate(raceDocument: Document): List<String>? =
        CrawlerUtil.selectElements(raceDocument, "font[size=4][face=Arial, Helvetica, sans-serif]").text()
            .split(" ")
            .filter {
                it.contains("\\d{2}/\\d{2}".toRegex())
            }

    private fun extractRaceNameWithDistance(raceDocument: Document): List<String>? =
        CrawlerUtil.selectElements(raceDocument, "font[size=3][face=Arial, Helvetica, sans-serif]").toList()
            .map {
                it.text()
            }

    private fun extractPlace(raceDocument: Document): List<String>? =
        CrawlerUtil.selectElements(raceDocument, "td[width=19%]").select("div[align=center]")
            .toList()
            .filter { it.text() != "장소" }
            .mapNotNull { it.text() }

    private fun getHostInfoElement(raceDocument: Document): Elements =
        CrawlerUtil.selectElements(raceDocument, "td[width=30%]").select("div[align=right][valign=bottom]")

    private fun extractHostInfo(elements: Elements): List<HostInfo> {
        return elements.map {
            val hostNameAndPhoneNumber = it.text().split("☎")
            val descriptionUrl = it.selectFirst("a")?.attributes()?.first()?.value
            HostInfo(
                hostName = hostNameAndPhoneNumber[0],
                phoneNumber = hostNameAndPhoneNumber[1],
                descriptionUrl = descriptionUrl
            )
        }
    }
}
