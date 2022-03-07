package com.example.justforpractice

import android.app.Application
import com.example.justforpractice.di.AppModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication() : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(AppModule.createModule())
        }
    }
}