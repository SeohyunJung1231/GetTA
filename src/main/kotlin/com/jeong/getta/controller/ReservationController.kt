package com.jeong.getta.controller

import com.jeong.getta.service.AircraftRentalService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reservations")
class ReservationController (
    private val service: AircraftRentalService
){
    @PostMapping("/{reservationId}")
    fun confirm(@PathVariable reservationId: Long): Boolean {
        service.confirm(reservationId)
        return true
    }
    @DeleteMapping("/{reservationId}/reject")
    fun reject(@PathVariable reservationId: Long): Boolean {
        service.reject(reservationId)
        return true
    }
    @DeleteMapping("/{reservationId}/cancel")
    fun cancel(@PathVariable reservationId: Long): Boolean {
        service.cancel(reservationId)
        return true
    }
}