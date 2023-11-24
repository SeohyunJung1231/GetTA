package com.jeong.getta.service

import com.jeong.getta.entity.*
import com.jeong.getta.repo.ScheduleRepository
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.*

class ScheduleServiceTest : ShouldSpec({
    val repository: ScheduleRepository = mockk()
    val target = ScheduleService(repository)
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
        clearMocks(repository)
    }

    beforeTest {
        every { repository.findById(any()) } answers { Optional.of(someSchedule) }
    }

    context("isAvailable") {
        should("return True if schedule is not reserved") {
            someSchedule.reservation = null

            target.isAvailable(someSchedule.id).shouldBeTrue()
        }
        should("return True if schedule is available") {
            someSchedule.reservation = someReservation
            someSchedule.reservation?.status = ReservationStatus.AVAILABLE

            target.isAvailable(someSchedule.id).shouldBeTrue()
        }
        should("return False if schedule is pending") {
            someSchedule.reservation = someReservation
            someSchedule.reservation?.status = ReservationStatus.PENDING

            target.isAvailable(someSchedule.id).shouldBeFalse()
        }
        should("return False if schedule is confirmed") {
            someSchedule.reservation = someReservation
            someSchedule.reservation?.status = ReservationStatus.CONFIRMED

            target.isAvailable(someSchedule.id).shouldBeFalse()
        }
    }

})
