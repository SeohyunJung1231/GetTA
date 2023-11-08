package com.jeong.getta.controller

import com.jeong.getta.domain.Schedule
import com.jeong.getta.repo.ScheduleRepository
import com.jeong.getta.service.AircraftRentalService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Tag(name = "스케줄 관리 API", description = "스케줄과 관련된 데모 API 입니다.")
@RestController
@RequestMapping("/schedules")
class ScheduleController(
    private val aircraftRentalService: AircraftRentalService,
    private val scheduleRepository: ScheduleRepository
) {
    @Operation(summary = "스케줄 상세 조회", description = "스케줄을 상세 조회합니다.")
    @GetMapping("/{scheduleId}")
    fun getSchedule(
        @Parameter(description = "스케줄 아이디") @PathVariable scheduleId: Long
    ): Schedule? {
        val schedule = scheduleRepository.findById(scheduleId).get()
        return Schedule(
            departures = schedule.departures,
            arrivals = schedule.arrivals,
            departTime = schedule.departTime,
            arriveTime = schedule.arriveTime,
            durationMin = schedule.durationMin,
            fare = schedule.fare
        )
    }
    @Operation(summary = "스케줄 목록 조회", description = "스케줄 목록을 조회합니다.")
    @GetMapping
    fun getSchedules(
        @Parameter(description = "출발지") @RequestParam departures: String,
        @Parameter(description = "도착지") @RequestParam arrivals: String,
        @Parameter(description = "날짜") @RequestParam date: LocalDate
    ): List<Schedule> {
        return listOf()
    }
    @Operation(summary = "스케줄 예약 요청", description = "스케줄 예약을 요청합니다.")
    @PostMapping("/{scheduleId}/reservations")
    fun requestReservation(
        @Parameter(description = "스케줄 아이디") @PathVariable scheduleId: Long,
        @Parameter(description = "대여자 아이디") @RequestParam renterId: Long
    ): Boolean {
        aircraftRentalService.requestBy(renterId, scheduleId)
        return true
    }
}