package com.example.mylabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.mylabs.ui.theme.MyLabsTheme

var firstName : String = "Daniel"
var lastName : String = "Bierman"

fun printName() : String{
    return "Your first name is: $firstName and your last name is: $lastName."
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyLabsTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Yellow) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name:String = printName(), modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyLabsTheme {
        Greeting("Android")
    }
}

// Following along with Week 1:
//Type inferred:
var i = 0
var j = "String"

//Strongly typed:
var l : Int = 0
var m : String = "Strong String"

//Constants - use 'val'
val s = "Weak Constant String"
val s2 : String = "Strong constant string"

//Functions:

fun aFunction() : String {
    return "a String"
}

//For no return type: (Unit is like void from java)
fun bFunction() : Unit {

}

//Parameters:
fun cFunction (param1:String, param2:Int, param3:Double) : Unit {

}
//Default Values
fun printStrings(param1:String = "Hello", param2:String = "World"){
    var result = "String 1: $param1, String 2: $param2"
}

//You can then use the function like: (inside example function to avoid syntax error)
fun exampleFunction(){
    printStrings(param1="New", param2="World")
    //if you don't assign vars they will go to default:
    printStrings(param2="help");
}

//Now functions can take in other functions:

fun takeOtherFunction(aCoolFunction : (String) -> Unit){
    var aString = "This is a String"
    aCoolFunction(aString) //running the function with aString
}

//The syntax for a function type is ( Parameter types) -> Return type

// You can then call with other functions like so:

fun example2Function(){
    takeOtherFunction(::printStrings)
    //Creating a lamdba function and passing it to takeOtherFunction right away
    takeOtherFunction {str1:String -> var len = str1.length }
}

// Google Gemini Explanation of the :: operator
// "The :: operator allows you to treat a function or a property as an object,
// which can then be passed around, stored, or invoked dynamically. function reference."

//To my understanding it just lets us pass in the function reference instead of trying to call the function.



