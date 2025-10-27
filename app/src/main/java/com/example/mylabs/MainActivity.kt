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
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mylabs.ui.theme.MyLabsTheme

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
//What type should widthSizeClass be?
fun LoginPage(modifier: Modifier = Modifier, size: WindowSizeClass) {
    val items = rememberSaveable { mutableStateListOf<ShoppingItem>() }
    var newItem = rememberSaveable { mutableStateOf("") }

    var selectedItem = remember { mutableStateOf<ShoppingItem?>(null) }

    val isTablet = size.widthSizeClass == WindowWidthSizeClass.Expanded

    val rowWidth = if(isTablet) 1.0f else 0.3f


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        modifier = modifier.padding(24.dp, 0.dp, 24.dp, 200.dp).fillMaxHeight().fillMaxWidth()
    )
    {
        Text("Shopping List")
        if(isTablet or (selectedItem.value == null))
        {
            Row(modifier=Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth(if (isTablet) rowWidth else 1.0f)) {
                    //Have a Row at the top for the button and TextField
                    Row {
                        TextField(
                            value = newItem.value,
                            onValueChange = { newStr -> newItem.value = newStr })
                        Button(onClick = {
                            items.add(
                                ShoppingItem(
                                    newItem.value,
                                    false
                                )
                            ) //What are you adding when you click the button?
                            newItem.value = ""
                        }) {
                            Text("Add item")
                        }
                    }
                    //Now have a dynamic-size column that grows as items are added in the ArrayList
                    LazyColumn {

                        //This generates a for-loop from 0 to items.size, and passes in index as the counter variable
                        items(items.size) { index ->
                            //return a row that is full-width, centered vertically, and space between the two items on each row:
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .clickable(onClick = { selectedItem.value = items[index] }),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                //The shopping item's name
                                Text(text = "Item: ${items[index].name}")
                                //A checkbox showing if an item is selected or now
                                Checkbox(checked = items[index].sel,
                                    //when the user clicks on the checkbox, change the object at that row to trigger a recomposition
                                    onCheckedChange = { newVal ->
                                        items[index] = items[index].copy(sel = newVal)
                                    })
                            }
                        }
                    }
                }
                //for when it's a tablet and selectedItem != null
                selectedItem.value?.let{
                    Column(modifier = Modifier.fillMaxWidth(1.0f-rowWidth)){
                        ItemDetails(selectedItem) //This shows the details page on the right side
                    }
                }
            }
        }
        else //there's an item selected
        {
            ItemDetails(selectedItem) //This shows the details page on the whole page
        }
    }

}

//a layout for showing a single item
@Composable
fun ItemDetails(selectedItem: MutableState<ShoppingItem?>) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(selectedItem.value!!.name)
            Text(selectedItem.value!!.sel.toString())
        }
        Button(
            modifier = Modifier.align(Alignment.BottomStart),
            onClick = { selectedItem.value = null }) {
            Text("Hide")
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



