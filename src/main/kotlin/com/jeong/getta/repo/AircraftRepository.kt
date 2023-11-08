package com.jeong.getta.repo

import com.jeong.getta.entity.Aircraft
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AircraftRepository : JpaRepository<Aircraft, Long> {
    fun findAllByOwnerId(ownerId: Long): List<Aircraft> = listOf()
}