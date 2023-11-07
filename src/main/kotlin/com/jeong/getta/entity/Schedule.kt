package com.jeong.getta.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val departures: String,
    val arrivals: String,
    val departTime: LocalDateTime,
    val arriveTime: LocalDateTime,
    val durationMin: Long,

    val aircraftId: Long
)