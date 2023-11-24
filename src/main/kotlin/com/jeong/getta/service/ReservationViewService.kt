package com.jeong.getta.service

import com.jeong.getta.domain.Aircraft
import com.jeong.getta.domain.ReservationInfo
import com.jeong.getta.domain.Schedule
import com.jeong.getta.entity.ReservationStatus
import com.jeong.getta.repo.AircraftRepository
import com.jeong.getta.repo.ReservationRepository
import org.springframework.stereotype.Service

@Service
class ReservationViewService(
    private val reservationRepository: ReservationRepository,
    private val aircraftRepository: AircraftRepository
) {

    fun getByOwnerId(id: Long): List<ReservationInfo> {
        val aircrafts = aircraftRepository.findAllByOwnerId(id)
        val ownedScheduleIds = aircrafts.flatMap { it.schedules }.map { it.id }
        val reservations = reservationRepository.findAllByScheduleIdIn(ownedScheduleIds.toSet())
        return reservations.map {
            val schedule = it.schedule
            val aircraft = it.schedule.aircraft
            ReservationInfo(
                schedule = Schedule(
                    departures = schedule.departures,
                    arrivals = schedule.arrivals,
                    departTime = schedule.departTime,
                    arriveTime = schedule.arriveTime,
                    fare = schedule.fare,
                    aircraftId = schedule.aircraft.id
                ),
                aircraft = Aircraft(
                    uuid = aircraft.uuid,
                    name = aircraft.name,
                    manufacturer = aircraft.manufacturer,
                    capacity = aircraft.capacity
                ),
                requestTime = it.initTime,
                status = it.status
            )
        }
    }


    fun getByRenterId(id: Long): List<ReservationInfo> {
        val reservations =
            reservationRepository.findAllByRenterIdAndStatusNot(id, ReservationStatus.AVAILABLE)
        return reservations.map {
            val aircraft = it.schedule.aircraft
            val schedule = it.schedule
            ReservationInfo(
                schedule = Schedule(
                    departTime = schedule.departTime,
                    arriveTime = schedule.arriveTime,
                    departures = schedule.departures,
                    arrivals = schedule.arrivals,
                    fare = schedule.fare,
                    aircraftId = aircraft.id
                ),
                status = it.status,
                requestTime = it.initTime,
                aircraft = Aircraft(
                    uuid = aircraft.uuid,
                    name = aircraft.name,
                    manufacturer = aircraft.manufacturer,
                    capacity = aircraft.capacity
                )
            )
        }
    }
}