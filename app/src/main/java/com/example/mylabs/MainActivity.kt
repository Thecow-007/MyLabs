package com.example.mylabs

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylabs.ui.theme.MyLabsTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.text.format
import kotlin.text.get

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val widthSizeClass = calculateWindowSizeClass(this)
            MyLabsTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.primary) { innerPadding ->
                    LoginPage(
                        modifier = Modifier.padding(innerPadding),
                        size = widthSizeClass
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
fun LoginPage(modifier: Modifier = Modifier, size: WindowSizeClass) {
    val chats = rememberSaveable { mutableStateListOf<Chat>() }
    val newChat = rememberSaveable { mutableStateOf("") }

    var selectedItem = remember { mutableStateOf<Chat?>(null) }

    val isTablet = size.widthSizeClass == WindowWidthSizeClass.Expanded

    val rowWidth = if(isTablet) 1.0f else 0.3f

    val formatter = DateTimeFormatter.ofPattern("E h:mm a")

    @Composable
    fun ChatItem (index:Int){
        Row(
            modifier = Modifier.fillMaxWidth()
                .clickable(onClick = { selectedItem.value = chats[index] }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if(chats[index].isSent){
                Image(
                    painter = painterResource( R.drawable.human ),
                    contentDescription="The sender",
                    modifier= Modifier.width(100.dp).height(100.dp)
                )
                Text(text = chats[index].message)
                Text(text = chats[index].time.format(formatter), fontSize = 10.sp)
            }else{
                Text(text = chats[index].time.format(formatter), fontSize = 10.sp)
                Text(text = chats[index].message)
                Image(
                    painter = painterResource( R.drawable.boy ),
                    contentDescription="The receiver",
                    modifier= Modifier.width(100.dp).height(100.dp)
                )

            }


        }
    }



    @Composable
    fun ChatList(modifier: Modifier){
        Column(modifier = modifier.fillMaxWidth(if (isTablet) rowWidth else 1.0f)) {
            LazyColumn (
                modifier = Modifier.weight(1f),
                reverseLayout = true
            ) {

                //This generates a for-loop from 0 to items.size, and passes in index as the counter variable
                items(chats.size) { index ->
                    ChatItem(index)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    chats.add(
                        Chat(
                            newChat.value,
                            true,
                            time = LocalDateTime.now()
                        )
                    )
                    newChat.value = ""
                }) {
                    Text("Send")
                }
                TextField(
                    value = newChat.value,
                    onValueChange = { newStr -> newChat.value = newStr },
                    modifier = Modifier.weight(1f))

                Button(onClick = {
                    chats.add(
                        Chat(
                            newChat.value,
                            false,
                            time = LocalDateTime.now()
                        )
                    )
                    newChat.value = ""
                }) {
                    Text("Receive")
                }
            }
        }
    }

    //a layout for showing a single item
    @Composable
    fun ItemDetails(selectedItem: MutableState<Chat?>, modifier: Modifier = Modifier) {
        Box(modifier = modifier.fillMaxSize()) {
            Column {
                Text(selectedItem.value!!.message)
                Text("isSent: " + selectedItem.value!!.isSent.toString())
                Button(
                    onClick = {
                        chats.remove(selectedItem.value)
                        selectedItem.value = null
                    }) {
                    Text("Delete")
                }
            }
            Button(
                modifier = Modifier.align(Alignment.BottomStart),
                onClick = { selectedItem.value = null }) {
                Text("Hide")
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        modifier = modifier.padding(24.dp, 0.dp, 24.dp, 24.dp).fillMaxHeight().fillMaxWidth()
    )
    {
        if((selectedItem.value == null))
        {
            ChatList(Modifier.fillMaxWidth())
        }
        else //there's an item selected
        {
            if(!isTablet){
                ItemDetails(selectedItem, modifier = modifier.fillMaxSize()) //This shows the details page on the whole page
            }else{
                Row(Modifier.fillMaxSize()) {
                    ChatList(modifier = Modifier.weight(0.4f)) // List on the left (40% width)
                    ItemDetails(
                        selectedItem = selectedItem,
                        modifier = modifier.weight(0.6f) // Details on the right (60% width)
                    )
                }
            }

        }
    }

}






//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyLabsTheme {
//        LoginPage()
//    }
//}



