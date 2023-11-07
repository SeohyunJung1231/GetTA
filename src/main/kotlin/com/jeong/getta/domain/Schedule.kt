package com.jeong.getta.domain

import java.time.LocalDateTime

data class Schedule(
    val departures: String,
    val arrivals: String,
    val departTime: LocalDateTime,
    val arriveTime: LocalDateTime,
    val durationMin: Long
)
