package com.l2code.tmdb

import com.l2code.tmdb.di.appModules
import org.koin.core.context.startKoin

fun configure() {
    startKoin {
        modules(appModules)
    }
}