package com.example.mylabs
data class DataRepository(
    var username:String = "",
    var password:String = "",
    var phone:String = "(000) 000-0000",
    var email:String = "default@gmail.com",
    var address:String  = "Default Address",
){
    companion object {
        var theInstance = DataRepository()
        fun getInstance() :DataRepository { return theInstance }
    }
}