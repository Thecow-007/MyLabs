package com.example.mylabs

import java.time.LocalDateTime

data class Chat(
    var message:String,
    var isSent:Boolean,
    val time: LocalDateTime
)