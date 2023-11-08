package com.jeong.getta.service

import com.jeong.getta.entity.*
import com.jeong.getta.repo.HistoryRepository
import com.jeong.getta.repo.ReservationRepository
import com.jeong.getta.repo.ScheduleRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.*

class AircraftRentalServiceTest : BehaviorSpec({

    val reservationRepository: ReservationRepository = mockk()
    val historyRepository: HistoryRepository = mockk()
    val scheduleRepository: ScheduleRepository = mockk()
    val aircraftRentalService =
        AircraftRentalService(PaymentService(), reservationRepository, historyRepository, scheduleRepository)

    val anyReservation = Reservation(
        renterId = 0L,
        initTime = LocalDateTime.now(),
        status = ReservationStatus.PENDING,
        schedule = Schedule(
            departures = "Seoul",
            arrivals = "Cheongju",
            departTime = LocalDateTime.now(),
            arriveTime = LocalDateTime.now().plusHours(2),
            durationMin = 60,
            aircraft = Aircraft(
                uuid = UUID.randomUUID().toString(),
                name = "JOBY",
                manufacturer = "조비에비에이션",
                fare = 3000000,
                ownerId = 1L
            )
        )
    )
    val anyHistory = History(
        time = LocalDateTime.now(), fromStatus = ReservationStatus.PENDING,
        toStatus = ReservationStatus.CONFIRMED,
        reservationId = anyReservation.id
    )

    given("예약 confirm 검증") {
        `when`("reservation status is PENDING") {
            anyReservation.status = ReservationStatus.PENDING
            every { reservationRepository.findById(any()) } answers { Optional.of(anyReservation) }
            every { historyRepository.save(any()) } answers { anyHistory }
            aircraftRentalService.confirm(anyReservation.id)
            then("reservation status has to be changed to CONFIRMED") {
                anyReservation.status shouldBe ReservationStatus.CONFIRMED
            }
        }
        `when`("reservation status is not PENDING") {
            anyReservation.status = ReservationStatus.CONFIRMED
            every { reservationRepository.findById(any()) } answers { Optional.of(anyReservation) }
            every { historyRepository.save(any()) } answers { anyHistory }
            then("it has to throw error") {
                shouldThrow<IllegalStateException> { aircraftRentalService.confirm(anyReservation.id) }
            }
        }
    }


    given("예약 reject 검증") {
        `when`("reservation status is PENDING") {
            anyReservation.status = ReservationStatus.PENDING
            every { reservationRepository.findById(any()) } answers { Optional.of(anyReservation) }
            every { historyRepository.save(any()) } answers { anyHistory }
            aircraftRentalService.reject(anyReservation.id)
            then("reservation status has to be changed to AVAILABLE") {
                anyReservation.status shouldBe ReservationStatus.AVAILABLE
            }
        }
        `when`("reservation status is not PENDING") {
            anyReservation.status = ReservationStatus.AVAILABLE
            every { reservationRepository.findById(any()) } answers { Optional.of(anyReservation) }
            every { historyRepository.save(any()) } answers { anyHistory }
            then("it has to throw error") {
                shouldThrow<IllegalStateException> { aircraftRentalService.reject(anyReservation.id) }
            }
        }
    }

    given("예약 cancel 검증") {
        `when`("reservation status is CONFIRMED") {
            anyReservation.status = ReservationStatus.CONFIRMED
            every { reservationRepository.findById(any()) } answers { Optional.of(anyReservation) }
            every { historyRepository.save(any()) } answers { anyHistory }
            aircraftRentalService.cancel(anyReservation.id)
            then("reservation status has to be changed to AVAILABLE") {
                anyReservation.status shouldBe ReservationStatus.AVAILABLE
            }
        }
        `when`("reservation status is not CONFIRMED") {
            anyReservation.status = ReservationStatus.PENDING
            every { reservationRepository.findById(any()) } answers { Optional.of(anyReservation) }
            every { historyRepository.save(any()) } answers { anyHistory }
            then("it has to throw error") {
                shouldThrow<IllegalStateException> { aircraftRentalService.cancel(anyReservation.id) }
            }
        }
    }
})
