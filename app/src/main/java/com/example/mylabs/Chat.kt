package com.example.mylabs

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
@Entity(tableName="Messages")
data class Chat(
    @PrimaryKey(autoGenerate=true)
    var id: Int = 0,
    var message:String = "",
    var isSent:Boolean = false,
    val time: LocalDateTime = LocalDateTime.now()
)