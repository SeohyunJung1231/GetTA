package com.jeong.getta.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Aircraft(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val uuid: String,
    val name: String,
    val manufacturer: String,
    val capacity: Short,

    val ownerId: Long //TODO @OneToMany로 FK 연결
)