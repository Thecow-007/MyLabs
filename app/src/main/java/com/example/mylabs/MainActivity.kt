package com.example.mylabs

import android.content.Context.SENSOR_SERVICE
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.mylabs.ui.theme.MyLabsTheme




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
                    DisplayText(
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
fun DisplayText(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val mainKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    val SHARED_PREFS_KEY = "VariableName"

    var isShowingDialog = remember {mutableStateOf(true)}

    var currentValue = remember {mutableStateOf("Hello World")}

    var agreeCollectData = remember{mutableStateOf(false) }

    val sharedPreferences = EncryptedSharedPreferences.create(
        "MyFileName" ,
        mainKey,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var storedValue = remember {mutableStateOf(sharedPreferences.getString(SHARED_PREFS_KEY, "Default")) }

    fun textBoxValueChanged(newValue: String){
        run {
            currentValue.value = newValue
            if (agreeCollectData.value) {
                with(sharedPreferences.edit())
                {
                    putString(SHARED_PREFS_KEY, newValue)
                    apply()
                }
            }
        }
    }

    Column {
        if(agreeCollectData.value){
            Text(text= "Good news! You let us know where you live!!! :)", modifier = modifier)
        }
        else{
            Text(text= "Hey don't you think its weird that you know where we are but we don't know where you live?? Enter your address.... Please??? ", modifier = modifier)
        }

        TextField(
            value=currentValue.value,
            onValueChange = {nv -> textBoxValueChanged(nv)}
        )

        Text(text= "Stored Value: ${storedValue.value}", modifier = modifier)

    }




    fun confirmClicked(){
        agreeCollectData.value = true
        isShowingDialog.value = false
    }

    fun dismissClicked(){
        agreeCollectData.value = false
        isShowingDialog.value = false
    }

    if(isShowingDialog.value)
        AlertDialog(
            onDismissRequest = {isShowingDialog.value = false},
            title = { Text(text = "Save Address Data?") },
            text = { Text("We use your address so that we can plan where to place our next store. This data is optional and not required for our service. ") },       //This below causes a recomposition
            confirmButton = {  Button( onClick = ::confirmClicked) { Text("I consent to my address being shared.")   }  },
            dismissButton = {  Button( onClick = ::dismissClicked) {Text("I do not consent to my address being shared.")    }  }
        )




}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyLabsTheme {
        DisplayText()
    }
}


