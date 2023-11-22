package com.jeong.getta.controller.reservation

import com.jeong.getta.domain.History
import com.jeong.getta.domain.ReservationInfo
import com.jeong.getta.repo.HistoryRepository
import com.jeong.getta.service.AircraftRentalService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "예약 API - 대여자", description = "대여자의 예약과 관련된 API 입니다.")
@RestController
@RequestMapping("/renters/{id}/reservations")
class RenterReservationControllerImpl(
    private val service: AircraftRentalService,
    private val historyRepository: HistoryRepository
) : ReservationViewController {

    @Operation(summary = "예약 요청", description = "대여자가 예약을 요청합니다.")
    @PostMapping("/{scheduleId}/reservations")
    fun request(
        @Parameter(description = "대여자 아이디") @RequestParam id: Long,
        @Parameter(description = "스케줄 아이디") @PathVariable scheduleId: Long
    ): Long {
        // check renter authority
        return service.requestBy(id, scheduleId).id
    }

    @Operation(summary = "예약 취소", description = "대여자가 확정된 예약을 취소합니다")
    @DeleteMapping("/{reservationId}/cancel")
    fun cancel(
        @Parameter(description = "대여자 아이디") @PathVariable id: Long,
        @Parameter(description = "예약 번호") @PathVariable reservationId: Long
    ): Boolean {
        // check renter authority
        service.cancel(reservationId)
        return true
    }


    @Operation(summary = "예약 목록 조회", description = "대여자가 예약 목록을 조회합니다.")
    @GetMapping
    override fun get(
        // check renter authority
        @Parameter(description = "대여자 아이디") @PathVariable id: Long
    ): List<ReservationInfo> {
        return service.getBy(id)
    }

    @Operation(summary = "예약 상세 조회", description = "대여자가 예약 상세 내역을 조회합니다.")
    @GetMapping("/{reservationId}")
    override fun get(
        @Parameter(description = "대여자 아이디") @PathVariable id: Long,
        @Parameter(description = "대여자 아이디") @PathVariable reservationId: Long,
    ): ReservationInfo {
        TODO("Not yet implemented")
    }

    @Operation(summary = "예약 히스토리 조회", description = "대여자가 예약 히스토리를 조회합니다.")
    @GetMapping("/history")
    override fun getHistory(
        @Parameter(description = "대여자 아이디") @PathVariable id: Long
    ): List<History> {
        // check renter authority
        return historyRepository.findAllByRenterId(id)
    }
}