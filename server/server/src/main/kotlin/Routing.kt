package ktor.cst8410

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/firstTest") {
            val login = call.receive<LoginQuery>()
            call.respond(HttpStatusCode.OK, login)
        }

        get("/secondTest") {
//            val login = call.receive<LoginQuery>()
            call.respondText("Second Test!")
        }
    }
}
