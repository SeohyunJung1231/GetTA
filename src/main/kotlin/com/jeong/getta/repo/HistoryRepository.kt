package com.jeong.getta.repo

import com.jeong.getta.entity.History
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HistoryRepository: JpaRepository<History, Long>