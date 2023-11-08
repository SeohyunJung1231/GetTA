package com.jeong.getta.service

import com.jeong.getta.repo.AircraftRepository
import org.springframework.stereotype.Service

@Service
class AircraftManageService(
    private val repository: AircraftRepository
) {
    fun update(aircraftId: Long, fare: Int): Boolean {
        val aircraft = repository.findById(aircraftId).get()
        aircraft.fare = fare

        repository.save(aircraft)
        return true
    }
}