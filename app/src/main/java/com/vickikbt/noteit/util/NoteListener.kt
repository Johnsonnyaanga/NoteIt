package com.vickikbt.noteit.util

interface NoteListener {

    fun onStarted()

    fun onSuccess(message: String)

    fun onFailure(message: String)

}