package com.l2code.tmdb.di

import com.l2code.tmdb.data.KtorHttpClient
import com.l2code.tmdb.presentation.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dataModules = module {
    single<HttpClient> { KtorHttpClient.httpClient }
}
val viewModelModules = module {
    viewModel { HomeViewModel() }
}

val appModules = listOf(dataModules, viewModelModules)