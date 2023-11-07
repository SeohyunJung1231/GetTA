package com.jeong.getta.controller

import com.jeong.getta.domain.Schedule
import com.jeong.getta.repo.ScheduleRepository
import com.jeong.getta.service.AircraftRentalService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/schedules")
class ScheduleController(
    private val aircraftRentalService: AircraftRentalService,
    private val scheduleRepository: ScheduleRepository
) {
    @GetMapping("/{scheduleId}")
    fun getSchedule(@PathVariable scheduleId: Long): Schedule? {
        val schedule = scheduleRepository.findById(scheduleId).get()
        return Schedule(
            departures = schedule.departures,
            arrivals = schedule.arrivals,
            departTime = schedule.departTime,
            arriveTime = schedule.arriveTime,
            durationMin = schedule.durationMin
        )
    }

    @GetMapping
    fun getSchedules(
        @RequestParam departures: String,
        @RequestParam arrivals: String,
        @RequestParam date: LocalDate
    ): List<Schedule> {
        return listOf()
    }

    @PostMapping("/{scheduleId}/reservations")
    fun requestReservation(
        @PathVariable scheduleId: Long,
        @RequestParam renterId: Long
    ): Boolean {
        aircraftRentalService.requestBy(renterId, scheduleId)
        return true
    }
}