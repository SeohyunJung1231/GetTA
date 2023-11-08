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

@Service
class AircraftRentalService(
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

    fun getBy(renterId: Long): List<ReservationInfo> {
        val reservations =
            reservationRepository.findAllByRenterIdAndStatusNot(renterId, ReservationStatus.AVAILABLE)
        return reservations.map {
            val aircraft = it.schedule.aircraft
            val schedule = it.schedule
            ReservationInfo(
                schedule = Schedule(
                    departTime = schedule.departTime,
                    arriveTime = schedule.arriveTime,
                    departures = schedule.departures,
                    arrivals = schedule.arrivals,
                    durationMin = schedule.durationMin,
                    fare = schedule.fare,
                    aircraft = Aircraft(
                        uuid = aircraft.uuid,
                        name = aircraft.name,
                        manufacturer = aircraft.manufacturer,
                        capacity = aircraft.capacity
                    )
                ),
                status = it.status,
                requestTime = it.initTime
            )
        }
    }


}