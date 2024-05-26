package com.mira.mira.data.model

data class Notification(
    val id: Int,
    val title: String,
    val description: String,
    val time: String,
    var isRead: Boolean
)
