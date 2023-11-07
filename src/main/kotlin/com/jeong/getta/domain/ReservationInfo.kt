package com.jeong.getta.domain

import com.jeong.getta.entity.ReservationStatus
import java.time.LocalDateTime

data class ReservationInfo( //TODO FE와 명세 맞추기
    val aircraft: Aircraft,
    val schedule: Schedule,
    val requestTime: LocalDateTime,
    val status: ReservationStatus
)


