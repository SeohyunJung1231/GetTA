package com.jeong.getta.service

import com.jeong.getta.domain.Schedule
import com.jeong.getta.repo.ScheduleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Service
class ScheduleService(
    private val repository: ScheduleRepository,
) {
    @Transactional
    fun update(id: Long, newSchedule: Schedule) {
        val oldSchedule = repository.findById(id).get()
        oldSchedule.apply {
            departures = newSchedule.departures
            arrivals = newSchedule.arrivals
            departTime = newSchedule.arriveTime
            arriveTime = newSchedule.arriveTime
            durationMin = Duration.between(departTime, arriveTime).toMinutes()
            fare = newSchedule.fare
        }
    }

    fun isNotReserved(id: Long): Boolean {
        val schedule = repository.findById(id).get()
        return schedule.reservation == null
    }
}