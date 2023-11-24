package com.jeong.getta.service

import com.jeong.getta.entity.History
import com.jeong.getta.entity.ReservationAction
import com.jeong.getta.entity.ReservationStatus
import com.jeong.getta.repo.HistoryRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class HistoryService(
    private val historyRepository: HistoryRepository
) {
    fun save(
        fromStatus: ReservationStatus,
        toStatus: ReservationStatus,
        action: ReservationAction,
        renterId: Long,
        reservationId: Long
    ) {
        historyRepository.save(
            History(
                time = LocalDateTime.now(),
                fromStatus = fromStatus,
                toStatus = toStatus,
                action = action,
                reservationId = reservationId,
                renterId = renterId
            )
        )

    }
}