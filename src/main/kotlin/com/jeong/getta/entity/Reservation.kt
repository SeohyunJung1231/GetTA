package com.jeong.getta.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val renterId: Long,
    val scheduleId: Long,
    val requestTime: LocalDateTime,
    val confirmTime: LocalDateTime
)