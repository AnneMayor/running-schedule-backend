package com.anne.domain.common.util

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

object CrawlerUtil {
    fun getDocument(url: String): Document {
        return Jsoup.connect(url).get()
    }
    fun getHtmlBody(url: String): Element {
        return Jsoup.connect(url).get().body()
    }
    fun selectElements(document: Document, key: String): Elements {
        return document.select(key)
    }
}
