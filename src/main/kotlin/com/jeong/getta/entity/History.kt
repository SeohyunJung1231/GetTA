package com.jeong.getta.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class History(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val time: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val fromStatus: ReservationStatus,
    @Enumerated(EnumType.STRING)
    val toStatus: ReservationStatus,
    val action: ReservationAction,
    val reservationId: Long,
    val renterId: Long
)
enum class ReservationAction {
    REQUEST, CONFIRM, REJECT, CANCEL
}