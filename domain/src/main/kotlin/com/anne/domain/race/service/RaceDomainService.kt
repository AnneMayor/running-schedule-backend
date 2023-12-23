package com.anne.domain.race.service

import com.anne.domain.race.dto.RaceDto
import com.anne.domain.race.mapper.RaceMapper
import com.anne.domain.race.repository.RaceRepository
import org.springframework.stereotype.Service

@Service
class RaceDomainService(
    private val raceRepository: RaceRepository,
    private val raceMapper: RaceMapper,
) {
    fun createRace(raceDto: RaceDto?): RaceDto? {
        val race = raceDto?.let { raceMapper.toEntity(it) } ?: return null
        return raceRepository.save(race).let(raceMapper::toDto)
    }
}
