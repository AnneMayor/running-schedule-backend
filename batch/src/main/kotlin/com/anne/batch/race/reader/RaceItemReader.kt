package com.anne.batch.race.reader

import com.anne.batch.common.util.WebCrawlingDataFormatterUtil.formatHostInfo
import com.anne.batch.common.util.WebCrawlingDataFormatterUtil.formatPlace
import com.anne.batch.common.util.WebCrawlingDataFormatterUtil.formatRaceDate
import com.anne.batch.common.util.WebCrawlingDataFormatterUtil.formatRaceNameWithDistance
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

class RaceItemReader : ItemReader<List<RaceItem>?> {
    override fun read(): List<RaceItem> {
        val raceUrl = UrlCreator.makeCurrentDateRaceUrl(Instant.now().epochSecond)

        val raceDocument = CrawlerUtil.getDocument(raceUrl)

        val raceDates = formatRaceDate(raceDocument)

        val raceNamesWithDistances = formatRaceNameWithDistance(raceDocument)

        val raceNames = raceNamesWithDistances.map {
                it.replace(
                    CrawlerUtil.REGEX_RACE_NAME.toRegex(), ""
                )
            }

        val places = formatPlace(raceDocument)

        val hostInfoElements = getHostInfoElement(raceDocument)

        val hostInfoList: List<HostInfo> = formatHostInfo(hostInfoElements)

        val entryFee = null // TODO("등록비 정보는 다른 곳에서 Crawling 해오는걸로!")
        val startOfRegistration = null // TODO("대회 신청일자 정보는 다른 곳에서 Crawling 해오는걸로!")
        val endOfRegistration = null // TODO("신청마감일 정보는 다른 곳에서 Crawling 해오는걸로!")

        val crawlingData = mutableMapOf<String, Any?>()

        for (index in raceDates.indices) {
            val raceDay = DateTimeConvertor.convertStringToLocalDate(
                "${LocalDateTime.now().year}-${
                    raceDates[index].replace(
                        "/",
                        "-"
                    )
                }"
            )
            val raceName = raceNames[index]
            crawlingData["day$index"] = raceDay
            crawlingData["name$index"] = raceName
            crawlingData["place$index"] = places[index]
            crawlingData["phoneNumber$index"] = hostInfoList[index].phoneNumber
            crawlingData["host$index"] = hostInfoList[index].hostName
            crawlingData["descriptionUrl$index"] = hostInfoList[index].descriptionUrl
        }

        val raceItemList = createRaceItemList(raceDates, crawlingData, entryFee, startOfRegistration, endOfRegistration)

        return raceItemList
    }

    private fun getHostInfoElement(raceDocument: Document): Elements =
        CrawlerUtil.selectElements(raceDocument, "td[width=30%]").select("div[align=right][valign=bottom]")

    private fun createRaceItemList(
        raceDates: List<String>,
        crawlingData: Map<String, Any?>,
        entryFee: Int?,
        startOfRegistration: Instant?,
        endOfRegistration: Instant?
    ): List<RaceItem> {
        val raceItemList = mutableListOf<RaceItem>()
        for (index in raceDates.indices) {
            raceItemList.add(
                RaceItem(
                    name = crawlingData["name$index"] as String?,
                    description = crawlingData["descriptionUrl$index"] as String?,
                    place = crawlingData["place$index"] as String?,
                    day = DateTimeConvertor.convertLocalDateToInstant(
                        crawlingData["day$index"] as LocalDate,
                        ZoneId.systemDefault()
                    ),
                    descriptionUrl = crawlingData["descriptionUrl$index"] as String?,
                    host = crawlingData["host$index"] as String?,
                    phoneNumber = crawlingData["phoneNumber$index"] as String?,
                    entryFee = entryFee,
                    startOfRegistration = startOfRegistration,
                    endOfRegistration = endOfRegistration,
                )
            )
        }

        return raceItemList
    }
}
