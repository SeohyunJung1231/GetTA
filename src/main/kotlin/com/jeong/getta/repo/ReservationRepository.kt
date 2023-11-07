package com.jeong.getta.repo

import com.jeong.getta.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long> {
    fun findAllByRenterId(renterId: Long): List<Reservation> = listOf()
}