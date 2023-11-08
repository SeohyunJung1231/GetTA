package com.jeong.getta.repo

import com.jeong.getta.entity.History
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface HistoryRepository: JpaRepository<History, Long> {

    @Query("""
        select h.time,
           h.from_status,
           h.to_status,
           h.action,
           s.departures,
           s.arrivals,
           s.depart_time,
           s.arrive_time,
           s.duration_min,
           s.fare,
           a.uuid,
           a.name,
           a.manufacturer,
           a.capacity
        from history h
                 join reservation r on r.id = h.reservation_id
                 join schedule s on s.id = r.schedule_id
                 join aircraft a on a.id = s.aircraft_id
        where h.renter_id = :renterId
        order by h.time
    """,
        nativeQuery = true
    ) //TODO Mapping 에러가 있어서 entity manager로 해결하기
    fun findAllByRenterId(renterId: Long): List<com.jeong.getta.domain.History> = listOf()
}