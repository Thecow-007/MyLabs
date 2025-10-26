package com.example.mylabs

import android.app.Activity
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.ContextWrapper
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import coil3.compose.AsyncImage
import com.example.mylabs.ui.theme.MyLabsTheme
//import androidx.activity.compose.LocalActivity //Unresolved reference 'LocalActivity'
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URLEncoder


//Shell commands:
// cd C:\Users\bierm\AppData\Local\Android\Sdk\platform-tools
// .\adb shell am start -W -a android.intent.action.VIEW -d "cst8410://login" com.example.mylabs
// .\adb shell am start -W -a android.intent.action.VIEW -d "cst8410://profile" com.example.mylabs

class MainActivity : ComponentActivity() {

    var firstName : String = "Daniel"
    var lastName : String = "Bierman"

    fun printName() : String{
        return "your first name is: $firstName and your last name is: $lastName"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyLabsTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.primary) { innerPadding ->
                    LoginPage(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onStart() {
        Log.w( "MainActivity", "In onStart() - The activity is now visible on screen" );
        super.onStart()
    }

    override fun onResume(){
        Log.w( "MainActivity", "In onResume() - The activity is now responding to user input" );
        super.onResume();
    }

    override fun onPause(){
        Log.w( "MainActivity", "In onPause()- The activity no longer responds to user input" );
        super.onPause();
    }

    override fun onStop(){
        Log.w( "MainActivity", "In onStop() - The activity is no longer visible" );
        super.onStop();
    }

    override fun onDestroy(){
        Log.w( "MainActivity", "In onDestroy() - Any memory used by the application is freed" );
        super.onDestroy();
    }


}

@Composable
fun LoginPage(modifier: Modifier = Modifier) {

    val client = HttpClient(Android){
        install(ContentNegotiation) {
            json()
        }
    }

    var input1 = remember { mutableStateOf("")}
    var input2 = remember { mutableStateOf("")}

    var result = remember { mutableStateOf("")}
//    Had to use LocalContext to get it to work
    val context = LocalContext.current
    val nextPage = Intent(context, SecondActivity::class.java)

    val dr = DataRepository.getInstance()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        modifier = Modifier.padding(24.dp, 0.dp, 24.dp, 200.dp).fillMaxHeight().fillMaxWidth()
    )
    {
        Row {
            Text("Super Duper Calculator!")
        }
        Row {
            Text("Result : " + result.value)
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            //input 1
            TextField(
                modifier = Modifier.weight(1f),
                label = {Text("input 1:")},
                value = input1.value,
                placeholder = {Text("type a number...")},
                onValueChange = {
                        newValue:String ->
                    input1.value = newValue
                }
            )
            //input 2
            TextField(
                modifier = Modifier.weight(1f),
                label = {Text("input 2:")},
                value = input2.value,
                placeholder = {Text("type a number...")},
                onValueChange = {
                        newValue:String ->
                    input2.value = newValue
                }
            )
        }

        Row (
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
//            Add Button
            Button(
                onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val response: HttpResponse = client.post("http://10.0.2.2:8080/add")
                    {
                        contentType(ContentType.Application.Json)
                        val input = CalcInput(input1.value, input2.value)
                        setBody(input)
                    }
                    val body =  response.body<CalcResult>()
                    result.value = body.result
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                )

            ){
                Text("+")
            }

//            Subtract Button
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val response: HttpResponse = client.post("http://10.0.2.2:8080/subtract")
                    {
                        contentType(ContentType.Application.Json)
                        val input = CalcInput(input1.value, input2.value)
                        setBody(input)
                    }
                    val body =  response.body<CalcResult>()
                    result.value = body.result
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                )
            ){
                Text("-")
            }

//            Multiply Button
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val response: HttpResponse = client.post("http://10.0.2.2:8080/multiply")
                    {
                        contentType(ContentType.Application.Json)
                        val input = CalcInput(input1.value, input2.value)
                        setBody(input)
                    }
                    val body =  response.body<CalcResult>()
                    result.value = body.result
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                )
            ){
                Text("x")
            }

//            Divide Button
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val response: HttpResponse = client.post("http://10.0.2.2:8080/divide")
                    {
                        contentType(ContentType.Application.Json)
                        val input = CalcInput(input1.value, input2.value)
                        setBody(input)
                    }
                    val body =  response.body<CalcResult>()
                    result.value = body.result
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                )
            ){
                Text("/")
            }
        }

        Row {
            Text("Curl Command: curl http://10.0.2.2:8080/add -H \"Content-type:application/json\" -d \"{\\\"input1\\\":\\\"5\\\",\\\"input2\\\":\\\"2\\\"}\"")
        }
//        curl http://localhost:8080/add -H "Content-type:application/json" -d "{\"input1\":\"5\",\"input2\":\"2\"}"
//        curl http://10.0.2.2:8080/add -H "Content-type:application/json" -d "{\"input1\":\"5\",\"input2\":\"2\"}"

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // 1. Define the URL your QR code will point to
            val serverUrl = "http://10.0.2.2:8080"

            // 2. URL-encode it
            val encodedUrl = URLEncoder.encode(serverUrl, "UTF-8")

            // 3. Build the final API URL to get the QR image
            val qrCodeUrl = "https://api.qrserver.com/v1/create-qr-code/?data=$encodedUrl&size=150x150"

            // 4. Use AsyncImage (from Coil) to load and display the image
            AsyncImage(
                model = qrCodeUrl,
                contentDescription = "Server URL QR Code"
            )
        }
    }
}

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyLabsTheme {
        LoginPage()
    }
}



