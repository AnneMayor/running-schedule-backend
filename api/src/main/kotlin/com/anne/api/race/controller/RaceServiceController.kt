package com.anne.api.race.controller

import com.anne.api.annotation.ApiPurpose
import com.anne.api.annotation.Purpose
import com.anne.api.race.mapper.RaceDtoMapper
import com.anne.api.race.service.RaceQueryService
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("running/race")
class RaceServiceController(
    private val raceQueryService: RaceQueryService,
    private val raceDtoMapper: RaceDtoMapper,
) {
    @ApiPurpose(target = [Purpose.USER])
    @GetMapping("{race-id}")
    fun getRunningRace(
        @PathVariable("race-id") raceId: String
    ) {
        // TODO("마라톤 레이스 정보 서비스 조회 API 구현하기.")
    }
}
