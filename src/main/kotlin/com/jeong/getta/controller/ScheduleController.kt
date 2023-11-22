package com.jeong.getta.controller

import com.jeong.getta.domain.Aircraft
import com.jeong.getta.domain.Schedule
import com.jeong.getta.entity.LandingSite
import com.jeong.getta.repo.AircraftRepository
import com.jeong.getta.repo.ScheduleRepository
import com.jeong.getta.service.AircraftRentalService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Tag(name = "스케줄 관리 API", description = "스케줄과 관련된 데모 API 입니다.")
@RestController
class ScheduleController(
    private val aircraftRentalService: AircraftRentalService,
    private val scheduleRepository: ScheduleRepository,
    private val aircraftRepository: AircraftRepository
) {
    @Operation(summary = "항공기의 스케줄 등록", description = "하나의 항공기에 여러개의 스케줄을 등록합니다.")
    @PostMapping("/owners/{id}/aircrafts/{aircraftId}/schedules")
    fun register(
        @Parameter(description = "소유자 아이디") @PathVariable id: Long,
        @Parameter(description = "항공기 아이디") @PathVariable aircraftId: Long,
        @RequestBody schedules: List<Schedule>
    ): List<Long> {
        // check owner authority
        val aircraft = aircraftRepository.findById(aircraftId).get() //null 일 경우 에러 코드 처리
        return scheduleRepository.saveAll(
            schedules.map {
                com.jeong.getta.entity.Schedule(
                    departures = it.departures,
                    arrivals = it.arrivals,
                    departTime = it.departTime,
                    arriveTime = it.arriveTime,
                    durationMin = it.durationMin,
                    fare = it.fare,
                    aircraft = aircraft
                )
            }
        ).map { it.id }
    }
    @Operation(summary = "스케줄 정보 수정", description = "하나의 스케줄 정보를 수정합니다.")
    @PatchMapping("/owners/{id}/schedules/{scheduleId}")
    fun update(
        @Parameter(description = "소유자 아이디") @PathVariable id: Long,
        @Parameter(description = "스케줄 아이디") @PathVariable aircraftId: Long,
    ) {
        // check owner authority
        // todo implement!

    }

    @Operation(summary = "스케줄 제거", description = "하나의 스케줄을 제거합니다.")
    @DeleteMapping("/owners/{id}/schedules/{scheduleId}")
    fun delete(
        @Parameter(description = "소유자 아이디") @PathVariable id: Long,
        @Parameter(description = "스케줄 아이디") @PathVariable aircraftId: Long,
    ) {
        // check owner authority
        // todo implement!

    }

    @Operation(summary = "스케줄 목록 조회", description = "스케줄 목록을 조회합니다.")
    @GetMapping("/schedules")
    fun get(
        @Parameter(description = "날짜", example = "2023-11-08") @RequestParam date: LocalDate,
        @Parameter(description = "출발지") @RequestParam departures: LandingSite,
        @Parameter(description = "도착지") @RequestParam arrivals: LandingSite
    ): List<Schedule> {
        return scheduleRepository.findAllBy(
            departures.toString(),
            arrivals.toString(),
            date
        ).map {
            Schedule(
                departures = it.departures,
                arrivals = it.arrivals,
                departTime = it.departTime,
                arriveTime = it.arriveTime,
                durationMin = it.durationMin,
                fare = it.fare,
                aircraft = Aircraft(
                    uuid = it.aircraft.uuid,
                    name = it.aircraft.name,
                    manufacturer = it.aircraft.manufacturer,
                    capacity = it.aircraft.capacity
                )
            )
        }
    }


    @Operation(summary = "스케줄 상세 조회", description = "스케줄을 상세 조회합니다.")
    @GetMapping("/schedules/{scheduleId}")
    fun get(
        @Parameter(description = "스케줄 아이디") @PathVariable scheduleId: Long
    ): Schedule? {
        val schedule = scheduleRepository.findById(scheduleId).get()
        val aircraft = schedule.aircraft
        return Schedule(
            departures = schedule.departures,
            arrivals = schedule.arrivals,
            departTime = schedule.departTime,
            arriveTime = schedule.arriveTime,
            durationMin = schedule.durationMin,
            fare = schedule.fare,
            aircraft = Aircraft(
                uuid = aircraft.uuid,
                name = aircraft.name,
                manufacturer = aircraft.manufacturer,
                capacity = aircraft.capacity
            )
        )
    }
}