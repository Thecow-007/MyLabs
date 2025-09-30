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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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

    val FIRSTNAME_KEY = "FirstName"
    val LASTNAME_KEY = "LastName"
    val ADDRESS_KEY = "Address"

    var isShowingDialog = remember {mutableStateOf(true)}



    var agreeCollectData = remember{mutableStateOf(false) }

    val sharedPreferences = EncryptedSharedPreferences.create(
        "Lab4" ,
        mainKey,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var firstName = remember {mutableStateOf(sharedPreferences.getString(FIRSTNAME_KEY, "") ?: "")}
    var lastName = remember {mutableStateOf(sharedPreferences.getString(LASTNAME_KEY, "") ?: "" )}
    var address = remember {mutableStateOf(sharedPreferences.getString(ADDRESS_KEY, "") ?: "" )}


    fun textBoxValueChanged(handler: MutableState<String>, newValue: String, key: String){
        run {
            handler.value = newValue
            if (agreeCollectData.value) {
                with(sharedPreferences.edit())
                {
                    putString(key, newValue)
                    apply()
                }
            }
        }
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        modifier = Modifier.padding(24.dp).fillMaxHeight().fillMaxWidth()
    )
    {
        Text("User Info:")
        TextField(
            label={
                Text("First Name:")
            },
            value=firstName.value,
            onValueChange = {nv -> textBoxValueChanged(firstName, nv, FIRSTNAME_KEY)}
        )
        TextField(
            label={
                Text("Last Name:")
            },
            value=lastName.value,
            onValueChange = {nv -> textBoxValueChanged(lastName, nv, LASTNAME_KEY)}
        )

        TextField(
            label={
                Text("Address:")
            },
            value=address.value,
            onValueChange = {nv -> textBoxValueChanged(address, nv, ADDRESS_KEY)}
        )

    }




    fun confirmClicked(){
        agreeCollectData.value = true
        isShowingDialog.value = false
    }

    fun dismissClicked(){
        agreeCollectData.value = false
        isShowingDialog.value = false

        run {
            with(sharedPreferences.edit())
            {
//                Clear the Encrypted Shared Preferences
                putString(FIRSTNAME_KEY, "")
                putString(LASTNAME_KEY, "")
                putString(ADDRESS_KEY, "")
                apply()
            }
        }
    }

    if(isShowingDialog.value)
        AlertDialog(
            onDismissRequest = {isShowingDialog.value = false},
            title = {
                Row{
                    Text(
                        text = "Let us have your Data?",
                        fontSize = 20.sp
                    )
                    Image(
                        painter = painterResource( R.drawable.pretty_please ),
                        contentDescription="Image of a very nice man very nicely asking you for your data",
                        modifier = Modifier.height(50.dp).width(50.dp)
                    )
                }
            },
            text = { Text("We use your address so that we can plan where to place our next store. This data is optional and not required for our service. ") },       //This below causes a recomposition
            confirmButton = {  Button( onClick = ::confirmClicked) { Text("I consent to my data being shared.")   }  },
            dismissButton = {  Button( onClick = ::dismissClicked) {Text("I do not consent to my data being shared.")    }  }
        )




}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyLabsTheme {
        DisplayText()
    }
}


