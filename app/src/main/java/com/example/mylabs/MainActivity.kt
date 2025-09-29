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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
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
                    DisplayLighting(
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
fun DisplayLighting(modifier: Modifier = Modifier) {
    var lightValue = remember { mutableFloatStateOf(0.0f) }
    var accelerometerValues = remember { mutableStateOf(listOf(0f, 0f, 0f)) } // For X, Y, Z

    var stepCountText = remember { mutableStateOf("Steps: N/A") }


    var sensorManager = LocalContext.current.getSystemService(SENSOR_SERVICE) as SensorManager

    DisposableEffect(Unit) {
        var lightingSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        var accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        var stepsSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        var initialSteps = -1f


        val sensorListener = object: SensorEventListener {
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
//            Leave Blank
            }

            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return
                when (event.sensor.type){
                    Sensor.TYPE_LIGHT -> {
                        lightValue.floatValue = event.values[0]
                    }
                    Sensor.TYPE_ACCELEROMETER -> {
                        // event.values contains X, Y, and Z data in an array
                        accelerometerValues.value = event.values.toList()
                    }
                    Sensor.TYPE_STEP_COUNTER -> {
                        val currentSteps = event.values[0]
                        if (initialSteps == -1f) {
                            initialSteps = currentSteps
                        }
                        val sessionSteps = (currentSteps - initialSteps).toInt()
                        stepCountText.value = "Steps: $sessionSteps"
                    }
                }
            }
        }

        sensorManager.registerListener(sensorListener, lightingSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(sensorListener, accelSensor, SensorManager.SENSOR_DELAY_NORMAL)

        if (stepsSensor == null) {
            stepCountText.value = "Steps: Not Available"
        } else {
            sensorManager.registerListener(sensorListener, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }

        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }





    Column {
        //    Light Sensor Display
        Column{
            Image( painter = painterResource( R.drawable.lightbulb ), contentDescription="An image of a lightbulb" )
            Text(
                text = "The Light value is ${lightValue.floatValue}",
                modifier = Modifier.testTag("light_text"),
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
            )
        }

//    Accelerometer Sensor Display
        Column{
            val (x, y, z) = accelerometerValues.value
            Image( painter = painterResource( R.drawable.accelerometer ), contentDescription="An image of a speedometer" )
            Text(
                text = "Accelerometer: X=$x, Y=$y, Z=$z",
                modifier = Modifier.testTag("accelerometer_text"),
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
            )
        }

        Column{
            Image( painter = painterResource( R.drawable.step_counter ), contentDescription="An image of a man walking" )
            Text(
                text = "Step Count: ${stepCountText.value}",
                modifier = Modifier.testTag("step_count_text"),
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
            )
        }

    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyLabsTheme {
        DisplayLighting()
    }
}


