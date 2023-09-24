package com.anne.domain.common.util

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldNotBe
import java.time.Instant

class CrawlerUtilTest : ShouldSpec({
    context("CrawlerUtil") {
        should("parseHtmlToDto") {
            val url = "https://www.naver.com"
            val document = CrawlerUtil.parseHtmlToDto(url)
            document shouldNotBe null
            document.body().allElements shouldNotBe null
            println(document.body().allElements)
        }

//        should("기능 구현중") {
//            val epoch = Instant.parse("2022-11-30T18:35:24.00Z").epochSecond
//            val url = "http://www.roadrun.co.kr/schedule/list.php?today=$epoch&todays=Y"
//            val document = CrawlerUtil.parseHtmlToDto(url)
//            println(document.body())
//        }
    }
})