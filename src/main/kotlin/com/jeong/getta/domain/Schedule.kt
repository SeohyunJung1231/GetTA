package com.jeong.getta.domain

import com.jeong.getta.entity.LandingSite
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
@Schema(description = "스케줄 DTO")
data class Schedule(
    @Schema(description = "출발지", nullable = false, example = "KIMPO")
    val departures: LandingSite,
    @Schema(description = "도착지", nullable = false, example = "CHUNGJU")
    val arrivals: LandingSite,
    @Schema(description = "출발 시간 (KST)", nullable = false, example = "2023-11-08T10:25:20.254Z")
    val departTime: LocalDateTime,
    @Schema(description = "도착 시간 (KST)", nullable = false, example = "2023-11-08T11:25:20.254Z")
    val arriveTime: LocalDateTime,
    @Schema(description = "항공 요금", nullable = false, example = "3000000")
    val fare: Int,
    @Schema(description = "항공 정보", nullable = false)
    val aircraftId: Long
)
