package com.example.mylabs

import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
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
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.mylabs.ui.theme.MyLabsTheme
import androidx.core.net.toUri


class SecondActivity : ComponentActivity() {

    var firstName : String = "Daniel"
    var lastName : String = "Bierman"

    fun printName() : String{
        return "your first name is: $firstName and your last name is: $lastName"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
        super.onCreate(savedInstanceState)

        val data: Uri? = intent.data
        val dr = DataRepository.getInstance()

        // Check if launched by the "profile" deep link
        if (data != null && data.scheme == "cst8410" && data.host == "profile") {
            val phone = data.getQueryParameter("phone")
            val email = data.getQueryParameter("email")
            val address = data.getQueryParameter("address")

            // Save any non-null values to repository
            if (phone != null) {
                dr.phone = phone
            }
            if (email != null) {
                dr.email = email
            }
            if (address != null) {
                dr.address = address
            }
        }

        enableEdgeToEdge()
        setContent {
            MyLabsTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.primary) { innerPadding ->
                    SecondPageContent(
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
fun SecondPageContent(modifier: Modifier = Modifier) {
    val dr = DataRepository.getInstance()

    var phone = remember { mutableStateOf(dr.phone)}
    var email = remember { mutableStateOf(dr.email)}
    var address = remember { mutableStateOf(dr.address)}


    val context = LocalContext.current
    val activity = context.findActivity()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        modifier = Modifier.padding(24.dp).fillMaxHeight().fillMaxWidth()
    ) {



        Text("Page 2")
        Text("Welcome back: " + dr.username)

//        Phone
        Row {
            TextField(
                label={
                    Text("Phone:")
                },
                value = phone.value,
                placeholder = {Text("phone...")},
                onValueChange = {
                        newValue:String ->
                    dr.phone = newValue
                    phone.value = newValue
                }
            )
            Button(
                onClick = {
                    val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                        data = "tel:${dr.phone}".toUri()
                    }
                    context.startActivity(phoneIntent)
                }
            ) {
                Text("Call")
            }
        }

//        Email
        Row {
            TextField(
                label={
                    Text("Email:")
                },
                value = email.value,
                placeholder = {Text("email...")},
                onValueChange = {
                        newValue:String ->
                    dr.email = newValue
                    email.value = newValue
                }
            )
            Button(
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply{
                        data = ("mailto:" + dr.email).toUri()
                        putExtra(Intent.EXTRA_SUBJECT, "Hey Stinky!")
                    }
                    context.startActivity(emailIntent)
                }
            ) {
                Text("Email")
            }
        }


//        Address
        Row {
            TextField(
                label={
                    Text("Address:")
                },
                value = address.value,
                placeholder = {Text("address...")},
                onValueChange = {
                        newValue:String ->
                    dr.address = newValue
                    address.value = newValue
                }
            )
            Button(
                onClick = {
                    val mapIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = ("geo:0,0?q=" + dr.address).toUri()
                    }
                    context.startActivity(mapIntent)
                }
            ) {
                Text("View on Map")
            }
        }


        Button(onClick = {
            activity?.finish()
        }){
            Text("Go back")
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

@Preview(showBackground = true)
@Composable
fun SecondPagePreview() {
    MyLabsTheme {
        SecondPageContent()
    }
}



