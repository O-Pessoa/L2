package com.l2code.tmdb.di

import com.l2code.tmdb.data.movie.PreferencesService
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<PreferencesService> { PreferencesService() }
}