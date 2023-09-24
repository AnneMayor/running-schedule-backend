package com.anne.domain.race.service

import com.anne.domain.common.util.CrawlerUtil
import org.springframework.stereotype.Service

@Service
class RaceDomainService {

    private val RACE_SITE = "http://marathon.pe.kr/index_calendar.html"

    fun crawlRaceInfo() {
        val document = CrawlerUtil.parseHtmlToDto(RACE_SITE)
        TODO("재귀적으로 탐색하며 javascript window 함수 실행해서 해당 화면 크롤링하는 기능 구현")
    }
}