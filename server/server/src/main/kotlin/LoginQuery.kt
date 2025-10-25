package ktor.cst8410
import kotlinx.serialization.Serializable

@Serializable
data class LoginQuery(var loginName:String, var password:String)