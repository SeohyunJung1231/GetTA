package com.jeong.getta.controller

import com.jeong.getta.domain.ReservationInfo
import com.jeong.getta.service.AircraftRentalService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/renters")
class RenterController (
    private val service: AircraftRentalService
){

    @GetMapping("/{renterId}/reservations")
    fun getReservation(@PathVariable renterId: Long): List<ReservationInfo> {
        return service.getBy(renterId)
    }
}