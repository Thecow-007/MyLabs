package com.example.mylabs

import android.os.Bundle
import android.util.Log
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mylabs.ui.theme.MyLabsTheme

class MainActivity : ComponentActivity() {

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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        modifier = Modifier.padding(24.dp, 0.dp, 24.dp, 200.dp).fillMaxHeight().fillMaxWidth()
    )
    {
        Text("week 7")
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyLabsTheme {
        LoginPage()
    }
}



