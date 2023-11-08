package com.jeong.getta.controller

import com.jeong.getta.domain.Aircraft
import com.jeong.getta.repo.AircraftRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "항공기 API", description = "항공기와 관련된 데모 API 입니다.")
@RestController
@RequestMapping("/aircraft")
class AircraftController(
    private val aircraftRepository: AircraftRepository,
) {

    @Operation(summary = "항공기 상세 조회", description = "하나의 항공기에 대한 상세 조회를 제공합니다.")
    @GetMapping("/{aircraftId}")
    fun get(
        @PathVariable aircraftId: Long
    ): Aircraft {
        val aircraft = aircraftRepository.findById(aircraftId).get()
        return Aircraft(
            uuid = aircraft.uuid,
            name = aircraft.name,
            manufacturer = aircraft.manufacturer,
            fare = aircraft.fare

        )
    }
}