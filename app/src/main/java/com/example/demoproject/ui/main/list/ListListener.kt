package com.example.demoproject.ui.main.list

interface ListListener {


    fun onStarted()
    fun onSuccess(responseData: Any?)
    fun onFailure(message: String?)
}
