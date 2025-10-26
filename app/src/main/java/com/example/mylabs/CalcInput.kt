package com.example.mylabs
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class CalcInput(
    @SerialName("input1")
    val input1: String,
    @SerialName("input2")
    val input2: String
)


