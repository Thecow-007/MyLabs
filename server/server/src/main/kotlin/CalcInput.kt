package ktor.cst8410
import kotlinx.serialization.Serializable

@Serializable
data class CalcInput(
    val input1: String,
    val input2: String
)