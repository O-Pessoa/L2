package com.l2code.tmdb.di

import com.l2code.tmdb.data.KtorHttpClient
import com.l2code.tmdb.data.movie.MovieRepository
import com.l2code.tmdb.data.movie.MovieRepositoryImpl
import com.l2code.tmdb.data.movie.MovieService
import com.l2code.tmdb.data.movie.MovieServiceImpl
import com.l2code.tmdb.data.movie.PreferencesRepository
import com.l2code.tmdb.data.movie.PreferencesRepositoryImpl
import com.l2code.tmdb.presentation.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dataModules = module {
    single<HttpClient> { KtorHttpClient.httpClient }
    single<MovieService> { MovieServiceImpl(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }
}
val viewModelModules = module {
    viewModel { HomeViewModel(get(), get()) }
}

expect val platformModule: Module

val appModules = listOf(platformModule, dataModules, viewModelModules)