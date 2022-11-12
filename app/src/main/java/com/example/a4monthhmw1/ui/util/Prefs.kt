package com.example.a4monthhmw1.ui.util

import android.content.SharedPreferences

class Prefs (private val preferences:SharedPreferences){

    fun saveBoardState(){
        preferences.edit().putBoolean("isShow",true).apply()
    }
    fun isBoardShow():Boolean{
        return preferences.getBoolean("isShow",false)
    }
}