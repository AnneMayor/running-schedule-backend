package com.anne.api.race.controller

import com.anne.api.annotation.ApiPurpose
import com.anne.api.annotation.Purpose
import com.anne.api.race.mapper.RaceDtoMapper
import com.anne.api.race.req.RaceCreateReq
import com.anne.api.race.res.RaceRes
import com.anne.api.race.service.RaceCommandService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*")
@RestController
@RequestMapping("running/race")
class RaceController(
    private val raceCommandService: RaceCommandService,
    private val raceDtoMapper: RaceDtoMapper,
) {
    @ApiPurpose(target = [Purpose.USER])
    @PostMapping
    fun createRunningRace(
        @RequestBody raceCreateReq: RaceCreateReq
    ): RaceRes? {
        return raceCommandService.createRaceOrNull(raceCreateReq)?.let(raceDtoMapper::toRes)
    }
}
