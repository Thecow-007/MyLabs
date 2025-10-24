package com.example.mylabs

data class DataRepository(var name:String = "", var age:Int = 0){
    companion object {
        var theInstance = DataRepository()
        fun getInstance() :DataRepository { return theInstance }
    }
}