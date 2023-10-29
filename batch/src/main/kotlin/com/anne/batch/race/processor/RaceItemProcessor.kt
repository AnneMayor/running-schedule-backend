package com.anne.batch.race.processor

import com.anne.batch.race.item.RaceItem
import com.anne.domain.race.dto.RaceDto
import org.springframework.batch.item.ItemProcessor

class RaceItemProcessor : ItemProcessor<RaceItem, RaceDto> {
    override fun process(item: RaceItem): RaceDto? {
        TODO("RaceItem 객체를 RaceDto 객체로 변환한다.")
    }
}
