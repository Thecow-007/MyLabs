package com.example.mylabs
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class CalcResult(
    @SerialName("result")
    val result: String,
)