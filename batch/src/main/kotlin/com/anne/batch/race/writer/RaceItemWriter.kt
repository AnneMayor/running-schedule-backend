package com.anne.batch.race.writer

import com.anne.domain.race.dto.RaceDto
import com.anne.domain.race.service.RaceDomainService
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter


class RaceItemWriter(
    private val raceDomainService: RaceDomainService,
) : ItemWriter<RaceDto?> {
    override fun write(chunk: Chunk<out RaceDto?>) {
        chunk.mapNotNull { raceDomainService.createRace(it) }
    }
}
