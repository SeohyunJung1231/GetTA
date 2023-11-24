package com.jeong.getta.service

import com.jeong.getta.domain.Aircraft
import com.jeong.getta.domain.ReservationInfo
import com.jeong.getta.domain.Schedule
import com.jeong.getta.entity.History
import com.jeong.getta.entity.Reservation
import com.jeong.getta.entity.ReservationAction
import com.jeong.getta.entity.ReservationStatus
import com.jeong.getta.repo.HistoryRepository
import com.jeong.getta.repo.ReservationRepository
import com.jeong.getta.repo.ScheduleRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

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
class ReservationService(
    private val paymentService: PaymentService,
    private val reservationRepository: ReservationRepository,
    private val historyRepository: HistoryRepository,
    private val scheduleRepository: ScheduleRepository
) {
    fun requestBy(renterId: Long, scheduleId: Long): Reservation {
        val reservation = reservationRepository.save(
            Reservation(
                renterId = renterId,
                schedule = scheduleRepository.findById(scheduleId).get(),
                initTime = LocalDateTime.now(),
                status = ReservationStatus.PENDING
            )
        )

        paymentService.makePayment()

        historyRepository.save(
            History(
                time = LocalDateTime.now(),
                fromStatus = ReservationStatus.AVAILABLE,
                toStatus = ReservationStatus.PENDING,
                action = ReservationAction.REQUEST,
                reservationId = reservation.id,
                renterId = reservation.renterId
            )
        )
        return reservation
    }

    fun confirm(reservationId: Long): Long {
        val reservation = reservationRepository.findById(reservationId).get()
        check(reservation.status == ReservationStatus.PENDING)

        reservation.status = ReservationStatus.CONFIRMED
        historyRepository.save(
            History(
                time = LocalDateTime.now(),
                fromStatus = ReservationStatus.PENDING,
                toStatus = reservation.status,
                action = ReservationAction.CONFIRM,
                reservationId = reservationId,
                renterId = reservation.renterId
            )
        )
        return reservation.id
    }

    fun reject(reservationId: Long) {
        val reservation = reservationRepository.findById(reservationId).get()
        check(reservation.status == ReservationStatus.PENDING)

        paymentService.makeRefund()

        reservation.status = ReservationStatus.AVAILABLE
        historyRepository.save(
            History(
                time = LocalDateTime.now(),
                fromStatus = ReservationStatus.PENDING,
                toStatus = reservation.status,
                action = ReservationAction.REJECT,
                reservationId = reservationId,
                renterId = reservation.renterId
            )
        )
    }

    fun cancel(reservationId: Long): Boolean {
        val reservation = reservationRepository.findById(reservationId).get()
        check(reservation.status == ReservationStatus.CONFIRMED)

        paymentService.makeRefund()

        reservation.status = ReservationStatus.AVAILABLE
        historyRepository.save(
            History(
                time = LocalDateTime.now(),
                fromStatus = ReservationStatus.CONFIRMED,
                toStatus = reservation.status,
                action = ReservationAction.CANCEL,
                reservationId = reservationId,
                renterId = reservation.renterId
            )
        )
        return true
    }

    fun getBy(reservationId: Long) : ReservationInfo {
        val reservation = reservationRepository.findById(reservationId).get()
        val schedule = reservation.schedule
        val aircraft = reservation.schedule.aircraft
        return ReservationInfo(
            schedule = Schedule(
                departures = schedule.departures,
                arrivals = schedule.arrivals,
                departTime = schedule.departTime,
                arriveTime = schedule.arriveTime,
                fare = schedule.fare,
                aircraftId = aircraft.id
            ),
            aircraft = Aircraft(
                uuid = aircraft.uuid,
                name = aircraft.name,
                manufacturer = aircraft.manufacturer,
                capacity = aircraft.capacity
            ),
            requestTime = reservation.initTime,
            status = reservation.status
        )
    }

}