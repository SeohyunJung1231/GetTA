package com.jeong.getta.repo

import com.jeong.getta.entity.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ScheduleRepository: JpaRepository<Schedule, Long> {

    @Query("""
        select * from schedule
        where departures = :departures and arrivals = :arrivals and date(depart_time) = :departDate
        """,
        nativeQuery = true)
    fun findAllBy(departures: String, arrivals: String, departDate: LocalDate): List<Schedule> = listOf()
}