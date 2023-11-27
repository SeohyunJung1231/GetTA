package com.jeong.getta.controller.reservation

import com.jeong.getta.domain.ReservationInfo

interface ReservationViewController {

    fun getAll(id: Long): List<ReservationInfo>
    fun get(id: Long, reservationId: Long): ReservationInfo
}