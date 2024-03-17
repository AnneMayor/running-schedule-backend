package com.anne.batch.common.util

import com.anne.batch.race.reader.HostInfo
import com.anne.domain.common.util.CrawlerUtil
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

object WebCrawlingDataFormatterUtil {

    fun formatRaceDate(raceDocument: Document): List<String> =
        CrawlerUtil.selectElements(raceDocument, "font[size=4][face=Arial, Helvetica, sans-serif]").text()
            .split(" ")
            .filter {
                it.contains("\\d{2}/\\d{2}".toRegex())
            }

    fun formatRaceNameWithDistance(raceDocument: Document): List<String> =
        CrawlerUtil.selectElements(raceDocument, "font[size=3][face=Arial, Helvetica, sans-serif]").toList()
            .map {
                it.text()
            }

    fun formatPlace(raceDocument: Document): List<String> =
        CrawlerUtil.selectElements(raceDocument, "td[width=19%]").select("div[align=center]")
            .toList()
            .filter { it.text() != "장소" }
            .mapNotNull { it.text() }

    fun formatHostInfo(elements: Elements): List<HostInfo> {
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
