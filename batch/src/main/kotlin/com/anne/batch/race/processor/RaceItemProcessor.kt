package com.anne.batch.race.processor

import com.anne.batch.race.item.RaceItem
import com.anne.domain.race.dto.RaceDto
import org.springframework.batch.item.ItemProcessor

class RaceItemProcessor : ItemProcessor<RaceItem, RaceDto> {
    override fun process(item: RaceItem): RaceDto? {
        return RaceDto(
            name = item.name,
            description = item.description,
            place = item.place,
            day = item.day,
            descriptionUrl = item.descriptionUrl,
            host = item.host,
            entryFee = item.entryFee,
            startOfRegistration = item.startOfRegistration,
            endOfRegistration = item.endOfRegistration,
        )
    }
}
