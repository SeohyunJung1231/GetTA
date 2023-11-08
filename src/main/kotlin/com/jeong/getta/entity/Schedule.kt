package com.jeong.getta.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Enumerated(EnumType.STRING)
    val departures: LandingSite,
    @Enumerated(EnumType.STRING)
    val arrivals: LandingSite,
    val departTime: LocalDateTime,
    val arriveTime: LocalDateTime,
    val durationMin: Long,
    var fare: Int,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val aircraft: Aircraft
)


enum class LandingSite {
    KIMPO, SEONGNAM, CHEONGJU, CHUNGJU, BUSAN, JEJU
}