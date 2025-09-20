package com.l2code.tmdb.data

import com.l2code.tmdb.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorHttpClient {
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                json = Json {
                    encodeDefaults = false
                    explicitNulls = false
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Logging) {
            level = LogLevel.NONE
            logger = object : Logger {
                override fun log(message: String) {
                    com.l2code.tmdb.log.d(message, tag = "Ktor")
                }
            }
        }
        defaultRequest {
            url.takeFrom(BASE_URL)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            bearerAuth(AppConfig.TMDB_BEARER_TOKEN)
        }
    }
    private const val BASE_URL = AppConfig.TMDB_BASE_URL
}