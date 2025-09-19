package com.l2code.tmdb

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform