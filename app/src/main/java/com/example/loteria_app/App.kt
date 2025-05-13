package com.example.loteria_app

import android.app.Application
import com.example.loteria_app.data.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getInstance(this)
    }
}