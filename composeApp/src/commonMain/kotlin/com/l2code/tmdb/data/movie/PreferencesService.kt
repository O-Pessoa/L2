package com.l2code.tmdb.data.movie

// Outra maneira seria, esse serviço expor apenas operações genéricas de set/get por chave e valor.
// A lógica de serialização (JSON encode/decode) ficaria fora do PreferencesService,
// evitando acoplamento com funcionalidades específicas como favoritos ou watchlist.
expect class PreferencesService {
    suspend fun setFavorites(movies: List<MovieDto>)
    suspend fun getFavorites(): List<MovieDto>

    suspend fun setWatchlist(movies: List<MovieDto>)
    suspend fun getWatchlist(): List<MovieDto>

}