package com.anne.api.race.service

import com.anne.api.race.mapper.RaceDtoMapper
import com.anne.api.race.req.RaceCreateReq
import com.anne.domain.race.dto.RaceDto
import com.anne.domain.race.service.RaceDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class RaceCommandService(
    private val raceDomainService: RaceDomainService,
    private val raceDtoMapper: RaceDtoMapper,
) {

    fun createRaceOrNull(raceCreateReq: RaceCreateReq): RaceDto? {
        return raceDomainService.createRace(raceDomainService.createRace(raceDtoMapper.toDto(raceCreateReq)))
    }
}
