package com.anne.domain.race.mapper

import com.anne.domain.race.dto.RaceDto
import com.anne.domain.race.entity.Race
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
)
interface RaceMapper {
    fun toEntity(dto: RaceDto?): Race?

    fun toDto(entity: Race?): RaceDto?
}