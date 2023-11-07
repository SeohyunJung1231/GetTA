package com.jeong.getta.controller

import com.jeong.getta.domain.Aircraft
import com.jeong.getta.repo.AircraftRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/owners")
class OwnerController(
    private val aircraftRepository: AircraftRepository
) {

    @PostMapping("/{ownerId}/aircrafts")
    fun registerAircraft(
        @PathVariable ownerId: Long,
        @RequestBody aircraft: Aircraft
    ): Boolean {
        aircraftRepository.save(
            com.jeong.getta.entity.Aircraft(
                uuid = aircraft.uuid,
                name = aircraft.name,
                manufacturer = aircraft.manufacturer,
                fare = aircraft.fare,
                ownerId = ownerId
            )
        )
        return true
    }

    @GetMapping("/{ownerId}/aircrafts")
    fun getAircraft(
        @PathVariable ownerId: Long
    ): List<Aircraft> {
        val aircrafts = aircraftRepository.findAllByOwnerId(ownerId)
        return aircrafts.map{
            Aircraft(
                uuid = it.uuid,
                name = it.name,
                manufacturer = it.manufacturer,
                fare = it.fare
            )
        }
    }
}