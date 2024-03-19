package com.anne.batch.race.processor

import com.anne.batch.race.item.RaceItem
import com.anne.domain.race.dto.RaceDto
import org.springframework.batch.item.ItemProcessor

class RaceItemProcessor : ItemProcessor<List<RaceItem>, List<RaceDto>> {
    override fun process(item: List<RaceItem>): List<RaceDto> {
        return item.map {
            RaceDto(
                name = it.name,
                description = it.description,
                place = it.place,
                day = it.day,
                descriptionUrl = it.descriptionUrl,
                host = it.host,
                entryFee = it.entryFee,
                startOfRegistration = it.startOfRegistration,
                endOfRegistration = it.endOfRegistration,
            )
        }
    }
}
