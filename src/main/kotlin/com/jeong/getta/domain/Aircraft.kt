package com.jeong.getta.domain

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "항공기 DTO")
data class Aircraft(
    @Schema(description = "항공기 유일 아이디", nullable = false, example = "3049601xfi3")
    val uuid: String,
    @Schema(description = "항공기 명", nullable = false, example = "JOBY S4")
    val name: String,
    @Schema(description = "제조사 명", nullable = false, example = "Joby Aviation")
    val manufacturer: String,
    @Schema(description = "정원", nullable = false, example = "4")
    val capacity: Short,
)
