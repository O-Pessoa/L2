package com.l2code.tmdb.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.l2code.tmdb.presentation.components.SearchTextField
import com.l2code.tmdb.resources.Resources
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tmdb.composeapp.generated.resources.Res
import tmdb.composeapp.generated.resources.foto_perfil

@Composable
fun HomeScreen(navController: NavController = rememberNavController(), viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    if(uiState.catalogResult is CatalogResult.Error && uiState.catalogResult !== null) {
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text(text = "Error!") },
            text = { Text(text = uiState.catalogResult?.message ?: Resources.Strings.ERROR_UNKNOWN) },
            confirmButton = {
                TextButton(onClick = { viewModel.clearError() }){
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
                                0.0f to Color(128,0,255),
                                1.0f to Color.Transparent,
                            ),
                            start = Offset.Zero,
                            end = Offset(0f, Float.POSITIVE_INFINITY),
                        )
                    )
            )
            Column(
                modifier = Modifier.padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Header(uiState, viewModel::onSearchTextChanged, viewModel::searchMovies)
                MainContent(uiState, viewModel::loadMovies)
            }
        }
    }
}

@Composable
fun Header(
    uiState: HomeState,
    onSearchTextChanged: (String) -> Unit,
    onSearchClick: () -> Unit,
) {
    Column(){
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
            ){
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
fun MainContent(uiState: HomeState, loadMovies: (String) -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        for (rowMovies in uiState.rowsMovies){
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
                        if (index >= rowMovies.movies.lastIndex && !uiState.isLoading && rowMovies.hasNextPage) {
                            loadMovies(rowMovies.title)
                        }
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth(1f/2.5f)
                                .aspectRatio(2f / 3f)
                                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                        ) {
                            AsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                model = movie.posterPath,
                                contentDescription = movie.title,
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                }
                if(uiState.isLoading && rowMovies.movies.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}


