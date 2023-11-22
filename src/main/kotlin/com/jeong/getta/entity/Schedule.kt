package com.jeong.getta.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Enumerated(EnumType.STRING)
    var departures: LandingSite,
    @Enumerated(EnumType.STRING)
    var arrivals: LandingSite,
    var departTime: LocalDateTime,
    var arriveTime: LocalDateTime,
    var durationMin: Long,
    var fare: Int,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    val aircraft: Aircraft,

    @OneToOne(mappedBy = "schedule", fetch = FetchType.LAZY)
    @JoinColumn
    val reservation: Reservation? = null
)


enum class LandingSite {
    KIMPO, SEONGNAM, CHEONGJU, CHUNGJU, BUSAN, JEJU
}