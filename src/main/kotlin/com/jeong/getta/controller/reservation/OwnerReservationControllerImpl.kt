package com.jeong.getta.controller.reservation

import com.jeong.getta.domain.History
import com.jeong.getta.domain.ReservationInfo
import com.jeong.getta.repo.HistoryRepository
import com.jeong.getta.service.AircraftRentalService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "예약 API - 소유자", description = "소유자의 예약과 관련된 API 입니다.")
@RestController
@RequestMapping("/owners/{id}/reservations")
class OwnerReservationControllerImpl(
    private val service: AircraftRentalService,
    private val historyRepository: HistoryRepository
) : ReservationViewController {

    @Operation(summary = "예약 확정", description = "소유자가 요청된 예약을 확정합니다")
    @PostMapping("/{reservationId}")
    fun confirm(
        @Parameter(description = "소유자 아이디") @PathVariable id: Long,
        @Parameter(description = "예약 번호") @PathVariable reservationId: Long
    ): Long {
        // check owner authority
        return service.confirm(reservationId)
    }

    @Operation(summary = "예약 거부", description = "소유자가 요청된 약예을 거부합니다")
    @DeleteMapping("/{reservationId}/reject")
    fun reject(
        @Parameter(description = "소유자 아이디") @PathVariable id: Long,
        @Parameter(description = "예약 번호") @PathVariable reservationId: Long
    ): Boolean {
        // check owner authority
        service.reject(reservationId)
        return true
    }


    @Operation(summary = "예약 취소", description = "소유자가 확정된 예약을 취소합니다")
    @DeleteMapping("/{reservationId}/cancel")
    fun cancel(
        @Parameter(description = "소유자 아이디") @PathVariable id: Long,
        @Parameter(description = "예약 번호") @PathVariable reservationId: Long
    ): Boolean {
        // check owner authority
        service.cancel(reservationId)
        return true
    }

    @Operation(summary = "예약 목록 조회", description = "소유자가 예약 목록을 조회합니다.")
    @GetMapping
    override fun get(
        @Parameter(description = "소유자 아이디") @PathVariable id: Long
    ): List<ReservationInfo> {
        return service.getBy(id)
    }

    @Operation(summary = "예약 상세 조회", description = "소유자가 예약 상세 내역을 조회합니다.")
    @GetMapping("/{reservationId}")
    override fun get(
        @Parameter(description = "소유자 아이디") @PathVariable id: Long,
        @Parameter(description = "소유자 아이디") @PathVariable reservationId: Long,
    ): ReservationInfo {
        // check owner authority
        TODO("Not yet implemented")
    }

    @Operation(summary = "예약 히스토리 조회", description = "소유자가 예약 히스토리를 조회합니다.")
    @GetMapping("/history")
    override fun getHistory(
        @Parameter(description = "소유자 아이디") @PathVariable id: Long
    ): List<History> {
        // check owner authority
        return historyRepository.findAllByRenterId(id)
    }

}