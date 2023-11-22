package com.jeong.getta.controller

import com.jeong.getta.domain.Aircraft
import com.jeong.getta.repo.AircraftRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "항공기 API", description = "항공기와 관련된 데모 API 입니다.")
@RestController
@RequestMapping("/owners/{id}/aircrafts")
class AircraftController(
    private val aircraftRepository: AircraftRepository
) {

    @Operation(summary = "항공기 등록", description = "소유자가 항공기를 등록합니다.")
    @PostMapping
    fun register(
        @Parameter(description = "소유자 아이디") @PathVariable id: Long,
        @RequestBody aircraft: Aircraft
    ): Long {
        // check owner authority
        return aircraftRepository.save(
            com.jeong.getta.entity.Aircraft(
                uuid = aircraft.uuid,
                name = aircraft.name,
                manufacturer = aircraft.manufacturer,
                capacity = aircraft.capacity,
                ownerId = id
            )
        ).id
    }

    @Operation(summary = "항공기 목록 조회", description = "소유자가 등록한 항공기 목록을 제공합니다.")
    @GetMapping
    fun get(
        @Parameter(description = "소유자 아이디") @PathVariable id: Long
    ): List<Aircraft> {
        val aircrafts = aircraftRepository.findAllByOwnerId(id)
        return aircrafts.map {
            Aircraft(
                uuid = it.uuid,
                name = it.name,
                manufacturer = it.manufacturer,
                capacity = it.capacity
            )
        }
    }

    @Operation(summary = "항공기 상세 조회", description = "하나의 항공기에 대한 상세 조회를 제공합니다.")
    @GetMapping("/{aircraftId}")
    fun get(
        @Parameter(description = "소유자 아이디") @PathVariable id: Long,
        @Parameter(description = "항공기 아이디") @PathVariable aircraftId: Long
    ): Aircraft {
        val aircraft = aircraftRepository.findById(aircraftId).get()
        return Aircraft(
            uuid = aircraft.uuid,
            name = aircraft.name,
            manufacturer = aircraft.manufacturer,
            capacity = aircraft.capacity
        )
    }

    @Operation(summary = "등록된 항공기 제거", description = "등록한 항공기를 제거합니다..")
    @DeleteMapping("/{aircraftId}")
    fun delete(
        @Parameter(description = "항공기 아이디") @PathVariable aircraftId: Long
    ): Boolean {
        // check owner authority
        aircraftRepository.deleteById(aircraftId)
        return true
    }
}