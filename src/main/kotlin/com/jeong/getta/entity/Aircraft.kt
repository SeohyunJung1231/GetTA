package com.jeong.getta.entity

import jakarta.persistence.*

@Entity
data class Aircraft(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val uuid: String,
    val name: String,
    val manufacturer: String,
    val capacity: Short,

    val ownerId: Long,

    @OneToMany(mappedBy = "aircraft", orphanRemoval = true)
    val schedules: List<Schedule> = listOf()
)