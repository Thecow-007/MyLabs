package ktor.cst8410

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    val invalidMessage = CalcResult("Please enter two numbers!")

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/add") {
            val calcInput = call.receive<CalcInput>()
            val input1 = calcInput.input1.toDoubleOrNull()
            val input2 = calcInput.input2.toDoubleOrNull()


//            verify that the user inputted 2 numbers
            if(input1 == null || input2 == null){
                call.respond(HttpStatusCode.OK, invalidMessage)
            }
            else{
                val result = CalcResult("" + (input1.toInt() + input2.toInt()))

                call.respond(HttpStatusCode.OK, result)
            }
        }

        post("/subtract") {
            val calcInput = call.receive<CalcInput>()
            val input1 = calcInput.input1.toDoubleOrNull()
            val input2 = calcInput.input2.toDoubleOrNull()


//            verify that the user inputted 2 numbers
            if(input1 == null || input2 == null){
                call.respond(HttpStatusCode.OK, invalidMessage)
            }
            else{
                val result = CalcResult("" + (input1.toInt() - input2.toInt()))

                call.respond(HttpStatusCode.OK, result)
            }
        }

        post("/multiply") {
            val calcInput = call.receive<CalcInput>()
            val input1 = calcInput.input1.toDoubleOrNull()
            val input2 = calcInput.input2.toDoubleOrNull()


//            verify that the user inputted 2 numbers
            if(input1 == null || input2 == null){
                call.respond(HttpStatusCode.OK, invalidMessage)
            }
            else{
                val result = CalcResult("" + (input1.toInt() * input2.toInt()))

                call.respond(HttpStatusCode.OK, result)
            }
        }

        post("/divide") {
            val calcInput = call.receive<CalcInput>()
            val input1 = calcInput.input1.toDoubleOrNull()
            val input2 = calcInput.input2.toDoubleOrNull()


//            verify that the user inputted 2 numbers
            if(input1 == null || input2 == null){
                call.respond(HttpStatusCode.OK, invalidMessage)
            }
            else{
                val result = CalcResult("" + (input1 / input2))

                call.respond(HttpStatusCode.OK, result)
            }
        }
    }
}
