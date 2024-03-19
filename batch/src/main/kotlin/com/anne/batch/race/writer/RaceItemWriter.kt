package com.anne.batch.race.writer

import com.anne.domain.race.dto.RaceDto
import com.anne.domain.race.service.RaceDomainService
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter


class RaceItemWriter(
    private val raceDomainService: RaceDomainService,
) : ItemWriter<List<RaceDto>> {
    override fun write(chunk: Chunk<out List<RaceDto>>) {
        chunk.map {
            it.forEach(raceDomainService::createRace)
        }
    }
}
