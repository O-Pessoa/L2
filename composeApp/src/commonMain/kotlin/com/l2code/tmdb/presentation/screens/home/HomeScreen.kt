package com.l2code.tmdb.presentation.screens.home

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.l2code.tmdb.entities.Movie
import com.l2code.tmdb.presentation.components.SearchTextField
import com.l2code.tmdb.resources.Resources
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tmdb.composeapp.generated.resources.Res
import tmdb.composeapp.generated.resources.foto_perfil

@Composable
fun HomeScreen(navController: NavController = rememberNavController(), viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.catalogResult is CatalogResult.Error && uiState.catalogResult !== null) {
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text(text = "Error!") },
            text = { Text(text = uiState.catalogResult?.message ?: Resources.Strings.ERROR_UNKNOWN) },
            confirmButton = {
                TextButton(onClick = { viewModel.clearError() }) {
                    Text(text = "Ok")
                }
            }
        )
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colorStops = arrayOf(
                            0.35f to Color(48, 50, 67),
                            1.0f to Color(21, 21, 29)
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite,
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(
                        brush = Brush.linearGradient(
                            colorStops = arrayOf(
                                0.0f to Color(128, 0, 255),
                                1.0f to Color.Transparent,
                            ),
                            start = Offset.Zero,
                            end = Offset(0f, Float.POSITIVE_INFINITY),
                        )
                    )
            )
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                        end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                    ).verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Header(uiState, viewModel::onSearchTextChanged, viewModel::searchMovies)
                MainContent(
                    uiState,
                    viewModel::loadRowMovies,
                    viewModel::addFavorite,
                    viewModel::removeFavorite,
                    viewModel::addWatchlist,
                    viewModel::removeWatchlist,
                )
            }
        }
    }
}

@Composable
private fun Header(
    uiState: HomeState,
    onSearchTextChanged: (String) -> Unit,
    onSearchClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp, top = 32.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = Resources.Strings.HOME_HEADER_TITLE,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.width(30.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .aspectRatio(1f)
                    .background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(36.dp)
                        .aspectRatio(1f),
                    painter = painterResource(Res.drawable.foto_perfil),
                    contentDescription = Resources.Defaults.DEFAULT_URL_PROFILE,
                    contentScale = ContentScale.Fit,
                )
            }
        }
        SearchTextField(
            modifier = Modifier.padding(horizontal = 30.dp),
            value = uiState.textSearchField,
            onValueChange = onSearchTextChanged,
            onSearch = onSearchClick,
        )
    }
}

@Composable
private fun MainContent(
    uiState: HomeState,
    loadMovies: (RowMovies) -> Unit,
    addFavorite: (Movie) -> Unit,
    removeFavorite: (Movie) -> Unit,
    addWatchlist: (Movie) -> Unit,
    removeWatchlist: (Movie) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        uiState.rowsMovies.forEach { rowMovies ->
            key(rowMovies.title) {
                AnimatedVisibility(
                    visible = rowMovies.movies.isNotEmpty(),
                    enter = fadeIn(
                        animationSpec = tween(durationMillis = 800)
                    ) + slideInHorizontally(
                        animationSpec = tween(durationMillis = 800),
                        initialOffsetX = { it }
                    ),
                    exit = fadeOut(
                        animationSpec = tween(durationMillis = 300)
                    ) + slideOutHorizontally(
                        animationSpec = tween(durationMillis = 300),
                        targetOffsetX = { it }
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                            text = rowMovies.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            overflow = TextOverflow.Ellipsis,
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(start = 30.dp, end = 30.dp),
                        ) {
                            itemsIndexed(rowMovies.movies) { index, movie ->
                                if (index >= rowMovies.movies.lastIndex - 10 && !uiState.isLoading && rowMovies.hasNextPage) {
                                    loadMovies(rowMovies)
                                }
                                MovieCard(
                                    movie,
                                    addFavorite = { addFavorite(movie) },
                                    removeFavorite = { removeFavorite(movie) },
                                    addWatchlist = { addWatchlist(movie) },
                                    removeWatchlist = { removeWatchlist(movie) }
                                )
                            }
                        }
                        if (uiState.isLoading && rowMovies.movies.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun LazyItemScope.MovieCard(
    movie: Movie,
    addFavorite: () -> Unit,
    removeFavorite: () -> Unit,
    addWatchlist: () -> Unit,
    removeWatchlist: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillParentMaxWidth(1f / 2.5f)
            .aspectRatio(2f / 3f)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .clickable { expanded = true }
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = movie.posterPath,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (movie.isFavorite) {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        removeFavorite()
                    },
                    text = {
                        Text(Resources.Strings.HOME_RM_FAVORITE_ACTION, style = MaterialTheme.typography.labelSmall)
                    }
                )
            } else {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        addFavorite()
                    },
                    text = {
                        Text(Resources.Strings.HOME_ADD_FAVORITE_ACTION, style = MaterialTheme.typography.labelSmall)
                    }
                )
            }

            if(movie.isInWatchlist){
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        removeWatchlist()
                    },
                    text = {
                        Text(Resources.Strings.HOME_RM_WATCHLIST_ACTION, style = MaterialTheme.typography.labelSmall)
                    }
                )
            } else {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        addWatchlist()
                    },
                    text = {
                        Text(Resources.Strings.HOME_ADD_WATCHLIST_ACTION, style = MaterialTheme.typography.labelSmall)
                    }
                )
            }
        }
    }
}


