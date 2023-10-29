package com.anne.domain.race.mapper

import com.anne.domain.race.dto.RaceDto
import com.anne.domain.race.entity.Race
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper

@Mapper(
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
interface RaceMapper {
    fun toEntity(dto: RaceDto?): Race?

    fun toDto(entity: Race?): RaceDto?
}