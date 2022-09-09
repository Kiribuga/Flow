package com.example.flow.db

import android.app.Application

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }
}