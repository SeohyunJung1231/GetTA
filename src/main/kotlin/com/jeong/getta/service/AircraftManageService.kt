package com.jeong.getta.service

import com.jeong.getta.repo.ScheduleRepository
import org.springframework.stereotype.Service

@Service
class AircraftManageService(
    private val repository: ScheduleRepository
) {
    fun update(scheduleId: Long, fare: Int) {
        val schedule = repository.findById(scheduleId).get()
        schedule.fare = fare

        repository.save(schedule)
    }
}