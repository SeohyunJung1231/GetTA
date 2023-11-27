package com.jeong.getta.service

import com.jeong.getta.entity.Reservation
import com.jeong.getta.entity.ReservationStatus
import com.jeong.getta.repo.ReservationRepository
import com.jeong.getta.repo.ScheduleRepository
import org.springframework.stereotype.Service

/**
 * **예약 상태 문서**
 *
 * 항공기는 AVAILABLE, PENDING, CONFIRMED 예약 상태가 존재하며, 상태 변화 규칙은 다음과 같다.
 * 1. 대여자가 항공기에 예약 요청을 보내면, PENDING 상태이다.
 * 1. 소유자가 예약 요청을 받아드리면, CONFIRMED 상태로 변한다.
 * 1. 소유자는 예약 요청을 거부할 수 있으며, AVAILABLE 상태로 되돌아간다.
 * 1. 소유자와 대여자 모두 CONFIRMED 상태에서 취소할 수 있으며, AVAILABLE 상태로 되돌아간다.
 */

@Service
class ReservationManageService(
    private val paymentService: PaymentService,
    private val reservationRepository: ReservationRepository,
    private val scheduleRepository: ScheduleRepository
) {
    fun requestBy(renterId: Long, scheduleId: Long): Reservation {
        val reservation = reservationRepository.save(
            Reservation(
                renterId = renterId,
                schedule = scheduleRepository.findById(scheduleId).get(),
                status = ReservationStatus.PENDING
            )
        )

        paymentService.makePayment()
        return reservation
    }

    fun confirm(reservationId: Long) {
        val reservation = reservationRepository.findById(reservationId).get()
        check(reservation.status == ReservationStatus.PENDING)

        reservation.status = ReservationStatus.CONFIRMED
    }

    fun reject(reservationId: Long) {
        val reservation = reservationRepository.findById(reservationId).get()
        check(reservation.status == ReservationStatus.PENDING)

        paymentService.makeRefund()

        reservation.status = ReservationStatus.AVAILABLE
    }

    fun cancel(reservationId: Long) {
        val reservation = reservationRepository.findById(reservationId).get()
        check(reservation.status == ReservationStatus.CONFIRMED)

        paymentService.makeRefund()

        reservation.status = ReservationStatus.AVAILABLE
    }

}