package com.anne.api.race.mapper

import com.anne.api.race.req.RaceCreateReq
import com.anne.api.race.res.RaceAdminRes
import com.anne.domain.race.dto.RaceDto
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
)
interface RaceDtoMapper {
    fun toAdminRes(dto: RaceDto?): RaceAdminRes?

    fun toDto(req: RaceCreateReq?): RaceDto?
}
