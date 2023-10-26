package com.anne.batch.race.writer

import com.anne.domain.race.entity.Race
import com.anne.domain.race.service.RaceDomainService
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter


class RaceItemWriter(
    private val raceDomainService: RaceDomainService,
) : ItemWriter<Race?> {
    override fun write(chunk: Chunk<out Race?>) {
        TODO("processor에서 eneity로 변환한 데이터를 저장한다.")
    }
}
