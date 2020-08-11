package com.vickikbt.noteit.util

interface StateListener {

    fun onStarted()

    fun onSuccess(message: String)

    fun onFailure(message: String)

}