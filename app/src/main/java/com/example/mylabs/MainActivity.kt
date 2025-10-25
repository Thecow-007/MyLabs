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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import com.example.mylabs.ui.theme.MyLabsTheme
//import androidx.activity.compose.LocalActivity //Unresolved reference 'LocalActivity'
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json


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

    val client = HttpClient(Android){
        install(ContentNegotiation) {
            json()
        }
    }

    val response: HttpResponse = client.post("https://localhost:8080/firstTest")
    {
        contentType(ContentType.Application.Json)
        setBody(LoginRequest("Jet", "Brains"))
    }
    println(response.status)
}

@Composable
fun LoginPage(modifier: Modifier = Modifier) {
    var username = remember { mutableStateOf("")}
    var password = remember { mutableStateOf("")}
//    Had to use LocalContext to get it to work
    val context = LocalContext.current
    val nextPage = Intent(context, SecondActivity::class.java)

    val dr = DataRepository.getInstance()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        modifier = Modifier.padding(24.dp).fillMaxHeight().fillMaxWidth()
    )
    {
//        Username
        TextField(
            label={
                Text("Username:")
            },
            value = username.value,
            placeholder = {Text("username...")},
            onValueChange = {
                newValue:String ->
                dr.username = newValue
                username.value = newValue
            }
        )
//        Password
        TextField(
            label={
                Text("Password:")
            },
            value = password.value,
            placeholder = {Text("password...")},
            onValueChange = {
                    newValue:String ->
                dr.password = newValue
                password.value = newValue
            }
        )

        Button(onClick = {
            context.startActivity(  nextPage )
        }){
            Text("Login")
        }
        Button(
            onClick = {
                val loginIntent = Intent(Intent.ACTION_VIEW).apply {
                    //uses the cst8410 protocol + attributes
                    data = ("cst8410://profile/?phone=1234&email=torunse@algonquincollege.com&address=1385+Woodroffe+Avenue").toUri()
                }
                context.startActivity(loginIntent)
            }
        ) {
            Text("Prepopulate Page 2")
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



