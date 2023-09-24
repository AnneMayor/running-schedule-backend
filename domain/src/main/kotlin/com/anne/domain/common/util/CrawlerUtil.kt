package com.anne.domain.common.util

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object CrawlerUtil {
    fun parseHtmlToDto(url: String): Document {
        return Jsoup.connect(url).get()
    }
}
