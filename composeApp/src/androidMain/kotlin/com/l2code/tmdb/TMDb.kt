package com.l2code.tmdb

import android.app.Application
import com.l2code.tmdb.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TMDb: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TMDb)
            modules(appModules)
        }
    }
}