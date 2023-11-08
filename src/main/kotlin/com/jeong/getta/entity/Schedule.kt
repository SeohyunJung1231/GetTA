package com.jeong.getta.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val departures: LandingSite,
    val arrivals: LandingSite,
    val departTime: LocalDateTime,
    val arriveTime: LocalDateTime,
    val durationMin: Long,
    var fare: Int,

    @ManyToOne
    @JoinColumn
    val aircraft: Aircraft
)


enum class LandingSite {
    KIMPO, SEONGNAM, CHEONGJU, CHUNGJU, BUSAN, JEJU
}