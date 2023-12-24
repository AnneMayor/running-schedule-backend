package com.anne.domain.common.util

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldNotBe
import java.time.Instant

class CrawlerUtilTest : ShouldSpec({
    context("CrawlerUtil") {
        should("getHtlmBody") {
            val url = "http://www.naver.com"
            val body = CrawlerUtil.getHtmlBody(url)
            body shouldNotBe null
            body.allElements shouldNotBe null
//            println(body.allElements)
        }

        should("parseDocument") {
            val epoch = Instant.now().epochSecond
            val url = "http://www.roadrun.co.kr/schedule/list.php?today=$epoch&todays=Y"
            val document = CrawlerUtil.getDocument(url)
            val numOfRunningRace = document.select("font[color=red]").text()
            val host = CrawlerUtil.selectElements(document, "td[width=30%]").select("div[align=right][valign=bottom]")[0].selectFirst("a")
                ?.attributes()?.first()?.value
            document shouldNotBe null
            document.allElements shouldNotBe null
            numOfRunningRace shouldNotBe null
//            println(document)
            println(host)
        }
    }
})
