package com.anne.batch.race.processor

import com.anne.domain.race.dto.RaceDto
import com.anne.domain.race.entity.Race
import org.springframework.batch.item.ItemProcessor

class RaceItemProcessor : ItemProcessor<RaceDto, Race> {
    override fun process(item: RaceDto): Race? {
        TODO("reader에서 크롤링한 데이터를 저장하기 위하여 DTO 객체를 entity 객체로 변환한다.")
    }
}