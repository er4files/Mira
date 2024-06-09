package com.mira.mira.data.model

import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
data class Message(
    var senderId: String? = "",
    var senderName: String? = "",
    var text: String? = "",
    var timestamp: Long = 0
)
