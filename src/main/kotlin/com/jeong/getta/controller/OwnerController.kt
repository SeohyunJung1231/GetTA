package com.jeong.getta.controller

import com.jeong.getta.domain.Aircraft
import com.jeong.getta.entity.Schedule
import com.jeong.getta.repo.AircraftRepository
import com.jeong.getta.repo.ScheduleRepository
import com.jeong.getta.service.AircraftManageService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "소유자 API", description = "항공기 소유자와 관련된 데모 API 입니다.")
@RestController
@RequestMapping("/owners")
class OwnerController(
    private val aircraftManageService: AircraftManageService,
    private val scheduleRepository: ScheduleRepository,
    private val aircraftRepository: AircraftRepository
) {
    @Operation(summary = "항공기의 스케줄 등록", description = "하나의 항공기에 여러개의 스케줄을 등록합니다.")
    @PostMapping("/{ownerId}/aircrafts/{aircraftId}/schedules")
    fun register(
        @PathVariable ownerId: Long,
        @PathVariable aircraftId: Long,
        @RequestBody schedules: List<Schedule>
    ): Boolean {
        // check owner authority
        val aircraft = aircraftRepository.findById(aircraftId).get()
        scheduleRepository.saveAll(
            schedules.map {
                Schedule(
                    departures = it.departures,
                    arrivals = it.arrivals,
                    departTime = it.departTime,
                    arriveTime = it.arriveTime,
                    durationMin = it.durationMin,
                    aircraft = aircraft
                )
            }
        )
        return true
    }

    @Operation(summary = "소유자의 항공기 등록", description = "소유자가 항공기를 등록합니다.")
    @PostMapping("/{ownerId}/aircrafts")
    fun registerAircraft(
        @PathVariable ownerId: Long,
        @RequestBody aircraft: Aircraft
    ): Boolean {
        // check owner authority
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

    @Operation(summary = "소유자의 항공기 조회", description = "소유자가 등록한 항공기 목록을 조회합니다.")
    @GetMapping("/{ownerId}/aircrafts")
    fun getAircraft(
        @PathVariable ownerId: Long
    ): List<Aircraft> {
        val aircrafts = aircraftRepository.findAllByOwnerId(ownerId)
        return aircrafts.map {
            Aircraft(
                uuid = it.uuid,
                name = it.name,
                manufacturer = it.manufacturer,
                fare = it.fare
            )
        }
    }

    @Operation(summary = "등록된 항공기의 수정", description = "등록한 항공기의 요금을 수정합니다.")
    @PatchMapping("/{ownerId}/aircrafts/{aircraftId}")
    fun update(
        @PathVariable ownerId: Long,
        @PathVariable aircraftId: Long,
        @RequestParam fare: Int
    ): Boolean {
        // check owner authority
        return aircraftManageService.update(aircraftId, fare)
    }

    @Operation(summary = "등록된 항공기의 제거", description = "등록한 항공기를 제거합니다..")
    @DeleteMapping("/{ownerId}/aircrafts/{aircraftId}")
    fun delete(
        @PathVariable aircraftId: Long
    ): Boolean {
        // check owner authority
        aircraftRepository.deleteById(aircraftId)
        return true
    }

}