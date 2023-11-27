package com.jeong.getta.service

import com.jeong.getta.entity.*
import com.jeong.getta.repo.ReservationRepository
import com.jeong.getta.repo.ScheduleRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.*

class ReservationManageServiceTest : ShouldSpec({
    val reservationRepository: ReservationRepository = mockk()
    val scheduleRepository: ScheduleRepository = mockk()
    val target =
        ReservationManageService(PaymentService(), reservationRepository, scheduleRepository)

    val someSchedule = Schedule(
        departures = LandingSite.KIMPO,
        arrivals = LandingSite.CHEONGJU,
        departTime = LocalDateTime.now(),
        arriveTime = LocalDateTime.now().plusHours(2),
        durationMin = 60,
        fare = 3000000,
        aircraft = Aircraft(
            uuid = UUID.randomUUID().toString(),
            name = "JOBY",
            manufacturer = "조비에비에이션",
            capacity = 4,
            ownerId = 1L
        )
    )
    val someReservation = Reservation(
        renterId = 0L,
        schedule = someSchedule,
        initTime = LocalDateTime.now(),
        status = ReservationStatus.PENDING
    )

    afterTest {
        clearMocks(reservationRepository)
    }

    beforeTest {
        every { scheduleRepository.findById(any()) } answers { Optional.of(someSchedule) }
        every { reservationRepository.save(any()) } answers { someReservation }
        every { reservationRepository.findById(any()) } answers { Optional.of(someReservation) }
    }

    context("[예약 요청] ReservationManageService.requestBy") {
        should("return reservation with status PENDING") {
            someReservation.status = ReservationStatus.PENDING

            target.requestBy(0L, 0L).status shouldBe ReservationStatus.PENDING
        }
    }

    context("[예약 확정] ReservationManageService.confirm") {
        should("return reservation with status AVAILABLE") {
            someReservation.status = ReservationStatus.PENDING
            target.confirm(someReservation.id)

            someReservation.status shouldBe ReservationStatus.CONFIRMED
        }

        should("throw error if initial status != PENDING") {
            someReservation.status = ReservationStatus.AVAILABLE

            shouldThrow<IllegalStateException> { target.confirm(someReservation.id) }
        }
    }

    context("[예약 거부] ReservationManageService.reject") {
        should("return reservation with status AVAILABLE") {
            someReservation.status = ReservationStatus.PENDING
            target.reject(someReservation.id)

            someReservation.status shouldBe ReservationStatus.AVAILABLE
        }

        should("throw error if initial status != PENDING") {
            shouldThrow<IllegalStateException> { target.reject(someReservation.id) }
        }
    }

    context("[예약 취소] ReservationManageService.cancel") {
        should("return reservation with status AVAILABLE") {
            someReservation.status = ReservationStatus.CONFIRMED
            target.cancel(someReservation.id)

            someReservation.status shouldBe ReservationStatus.AVAILABLE
        }

        should("throw error if initial status != CONFIRMED") {
            shouldThrow<IllegalStateException> { target.cancel(someReservation.id) }

        }
    }

    context("[예약 요청과 거부] ReservationManageService.request but reject") {

        should("return reservation with status AVAILABLE") {
            someReservation.status = ReservationStatus.PENDING
            target.requestBy(0L, 0L)
            target.reject(someReservation.id)

            someReservation.status shouldBe ReservationStatus.AVAILABLE
        }
    }
    context("[예약 요청과 확정] ReservationManageService.request and confirm") {

        should("return reservation with status CONFIRMED") {
            someReservation.status = ReservationStatus.PENDING
            target.requestBy(0L, 0L)
            target.confirm(someReservation.id)

            someReservation.status shouldBe ReservationStatus.CONFIRMED
        }
    }
    context("[예약 요청 후 확정과 취소] ReservationManageService.request and confirm but cancel") {

        should("return reservation with status AVAILABLE") {
            someReservation.status = ReservationStatus.PENDING
            target.requestBy(0L, 0L)
            target.confirm(someReservation.id)
            target.cancel(someReservation.id)

            someReservation.status shouldBe ReservationStatus.AVAILABLE
        }
    }

})
