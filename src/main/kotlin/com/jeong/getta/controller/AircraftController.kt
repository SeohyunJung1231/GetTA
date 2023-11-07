package com.jeong.getta.controller

import com.jeong.getta.domain.Aircraft
import com.jeong.getta.entity.Schedule
import com.jeong.getta.repo.AircraftRepository
import com.jeong.getta.repo.ScheduleRepository
import com.jeong.getta.service.AircraftManageService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/aircraft")
class AircraftController(
    private val aircraftManageService: AircraftManageService,
    private val aircraftRepository: AircraftRepository,
    private val scheduleRepository: ScheduleRepository
) {
    @PostMapping("/{aircraftId}/schedules")
    fun register(@PathVariable aircraftId: Long, @RequestBody schedules: List<Schedule>): Boolean {
        scheduleRepository.saveAll(
            schedules.map {
                Schedule(
                    departures = it.departures,
                    arrivals = it.arrivals,
                    departTime = it.departTime,
                    arriveTime = it.arriveTime,
                    durationMin = it.durationMin,
                    aircraftId = aircraftId
                )
            }
        )
        return true
    }

    @GetMapping("/{aircraftId}")
    fun get(@PathVariable aircraftId: Long): Aircraft {
        val aircraft = aircraftRepository.findById(aircraftId).get()
        return Aircraft(
            uuid = aircraft.uuid,
            name = aircraft.name,
            manufacturer = aircraft.manufacturer,
            fare = aircraft.fare

        )
    }

    @PatchMapping("/{aircraftId}")
    fun update(@PathVariable aircraftId: Long, @RequestParam fare: Int): Boolean {
        return aircraftManageService.update(aircraftId, fare)
    }

    @DeleteMapping("/{aircraftId}")
    fun delete(@PathVariable aircraftId: Long): Boolean {
        aircraftRepository.deleteById(aircraftId)
        return true
    }

}