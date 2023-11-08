package com.jeong.getta.domain

import com.jeong.getta.entity.LandingSite
import com.jeong.getta.entity.ReservationAction
import com.jeong.getta.entity.ReservationStatus
import java.time.LocalDateTime

data class History(
    val time: LocalDateTime,
    val fromStatus: ReservationStatus,
    val toStatus: ReservationStatus,
    val action: ReservationAction,
    val departures: LandingSite,
    val arrivals: LandingSite,
    val departTime: LocalDateTime,
    val arriveTime: LocalDateTime,
    val durationMin: Long,
    val fare: Int,
    val uuid: String,
    val name: String,
    val manufacturer: String,
    val capacity: Short
)
