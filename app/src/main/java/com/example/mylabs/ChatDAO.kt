package com.example.mylabs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChatDAO {

    @Insert
    suspend fun insertMessage(message:Chat) :Long

    @Query("Select * from Messages")
    suspend fun getAllMessages(): List<Chat>

    @Delete
    suspend fun deleteMessage(message:Chat):Unit

}