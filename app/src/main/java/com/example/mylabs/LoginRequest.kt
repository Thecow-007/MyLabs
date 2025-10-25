package com.example.mylabs
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("loginName")
    val loginName: String,
    @SerialName("password")
    val password: String
)