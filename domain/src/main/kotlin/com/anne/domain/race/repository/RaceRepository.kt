package com.anne.domain.race.repository

import com.anne.domain.race.entity.Race
import org.springframework.data.jpa.repository.JpaRepository

interface RaceRepository : JpaRepository<Race, Long> {
}