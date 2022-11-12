package com.example.a4monthhmw1.ui

import android.app.Application
import android.content.SharedPreferences
import com.example.a4monthhmw1.data.NoteDatabase
import com.example.a4monthhmw1.ui.util.Prefs

class App: Application() {
    private lateinit var preferences: SharedPreferences
    companion object{
        lateinit var db:NoteDatabase
        lateinit var prefs:Prefs
    }

    override fun onCreate() {
        super.onCreate()
        db = NoteDatabase.getDbInstance(this)
        preferences = this.applicationContext
            .getSharedPreferences("settings", MODE_PRIVATE)
        prefs = Prefs(preferences)
    }
}