package com.jeong.getta.domain

import com.jeong.getta.entity.ReservationStatus
import java.time.LocalDateTime

data class ReservationInfo(
    val schedule: Schedule,
    val aircraft: Aircraft,
    val requestTime: LocalDateTime,
    val status: ReservationStatus
)


