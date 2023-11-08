package com.jeong.getta.controller

import com.jeong.getta.domain.Aircraft
import com.jeong.getta.repo.AircraftRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "항공기 API", description = "항공기와 관련된 데모 API 입니다.")
@RestController
@RequestMapping("/aircraft")
class AircraftController(
    private val aircraftRepository: AircraftRepository,
) {

    @Operation(summary = "항공기 상세 조회", description = "하나의 항공기에 대한 상세 조회를 제공합니다.")
    @GetMapping("/{aircraftId}")
    fun get(
        @Parameter(description = "항공기 아이디") @PathVariable aircraftId: Long,
        @RequestBody aircraft: Aircraft
    ): Aircraft {
        val aircraft = aircraftRepository.findById(aircraftId).get()
        return Aircraft(
            uuid = aircraft.uuid,
            name = aircraft.name,
            manufacturer = aircraft.manufacturer,
            capacity = aircraft.capacity

        )
    }
}