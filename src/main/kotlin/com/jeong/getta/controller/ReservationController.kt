package com.jeong.getta.controller

import com.jeong.getta.service.AircraftRentalService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "예약 관리 API", description = "항공기 예약과 관련된 데모 API 입니다.")
@RestController
@RequestMapping("/reservations")
class ReservationController(
    private val service: AircraftRentalService
) {
    /**
     * **예약 상태 문서**
     *
     * 항공기는 AVAILABLE, PENDING, CONFIRMED 예약 상태가 존재하며, 상태 변화 규칙은 다음과 같다.
     * 1. 대여자가 항공기에 예약 요청을 보내면, PENDING 상태이다.
     * 1. 소유자가 예약 요청을 받아드리면, CONFIRMED 상태로 변한다.
     * 1. 소유자는 예약 요청을 거부할 수 있으며, AVAILABLE 상태로 되돌아간다.
     * 1. 소유자와 대여자 모두 CONFIRMED 상태에서 취소할 수 있으며, AVAILABLE 상태로 되돌아간다.
     */

    @Operation(summary = "예약 요청된 항공기의 확정", description = "소유자가 예약 요청을 확정합니다")
    @PostMapping("/{reservationId}")
    fun confirm(
        @Parameter(description = "예약 번호") @PathVariable reservationId: Long
    ): Long {
        return service.confirm(reservationId)
    }

    @Operation(summary = "요청된 항공기 예약의 거부", description = "소유자가 예약 요청을 거부합니다")
    @DeleteMapping("/{reservationId}/reject")
    fun reject(
        @Parameter(description = "예약 번호") @PathVariable reservationId: Long
    ): Boolean {
        service.reject(reservationId)
        return true
    }

    @Operation(summary = "확정된 항공기 예약의 취소", description = "대여자 또는 소유자가 확정된 예약을 취소합니다")
    @DeleteMapping("/{reservationId}/cancel")
    fun cancel(
        @Parameter(description = "예약 번호") @PathVariable reservationId: Long
    ): Boolean {
        service.cancel(reservationId)
        return true
    }
}