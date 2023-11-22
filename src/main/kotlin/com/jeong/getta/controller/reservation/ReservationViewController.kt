package com.jeong.getta.controller.reservation

import com.jeong.getta.domain.History
import com.jeong.getta.domain.ReservationInfo

interface ReservationViewController {

    fun get(id: Long): List<ReservationInfo>
    fun get(id: Long, reservationId: Long): ReservationInfo
    fun getHistory(id: Long): List<History>
}