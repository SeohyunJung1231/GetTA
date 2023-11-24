package com.jeong.getta.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val renterId: Long,
    @OneToOne
    @JoinColumn
    val schedule: Schedule,
    val initTime: LocalDateTime = LocalDateTime.now(),
    val updateTime: LocalDateTime? = null,
    @Enumerated(EnumType.STRING)
    var status: ReservationStatus
)
enum class ReservationStatus {
    AVAILABLE, PENDING, CONFIRMED
}