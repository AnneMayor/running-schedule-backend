package com.anne.api.race.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
class RaceQueryService {
}