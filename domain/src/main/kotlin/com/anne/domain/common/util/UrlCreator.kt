package com.anne.domain.common.util

object UrlCreator {
    fun makeCurrentDateRaceUrl(epoch: Long): String {
        return "http://www.roadrun.co.kr/schedule/list.php?today=${epoch}&todays=Y"
    }
}
