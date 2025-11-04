package ktor.cst8410
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CalcResult(
    @SerialName("result")
    val result: String,
)