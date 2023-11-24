package com.jeong.getta.repo

import com.jeong.getta.entity.Reservation
import com.jeong.getta.entity.ReservationStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long> {
    fun findAllByRenterIdAndStatusNot(
        renterId: Long,
        status: ReservationStatus
    ): List<Reservation> = listOf()

    fun findAllByScheduleIdIn(scheduleIds: Set<Long>): Set<Reservation> = setOf()
}