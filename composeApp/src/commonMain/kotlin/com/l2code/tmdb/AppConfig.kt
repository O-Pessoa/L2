package com.l2code.tmdb

// Esses dados poderiam ser injetados pelo gradle.properties ou local.properties (Android) e .xcconfig ou Info.plist (IOS)
// Mas vou reduzir a complexidade nesse cenário XD
object AppConfig {
    const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    const val TMDB_BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZDM0MDZmYjEwYWNhMGUwMGI1ZWY3Njc3NDhjNmU2YyIsIm5iZiI6MTcwNzk0MjU5OS4zODMwMDAxLCJzdWIiOiI2NWNkMjJjNzhkMmY4ZDAxODZlYzY5NGIiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.4oAGrJfQOmBXf9_b98UukLCWFRvKaFPRH_XdSTeE4nA"
}