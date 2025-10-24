package com.example.mylabs

data class MyRepository(var name:String = "", var age:Int = 0){
    companion object {
        var theInstance = MyRepository()
        fun getInstance() :MyRepository { return theInstance }
    }
}