package com.mira.mira.data.model

data class Doctor(
    val no_hp: String,
    val spesialis: String,
    val profile_picture: String,
    val id: String,
    val email: String,
    val nama: String,
    val rating: Float = 4.0f // default rating
)
