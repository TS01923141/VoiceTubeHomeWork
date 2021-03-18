package com.example.voicetubehomework

import android.app.Application

class MainApplication : Application() {

    companion object {
        private lateinit var application: Application

        @JvmStatic
        public fun getInstance(): Application {
            return application
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}