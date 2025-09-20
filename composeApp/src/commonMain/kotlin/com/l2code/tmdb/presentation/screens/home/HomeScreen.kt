package com.l2code.tmdb.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.l2code.tmdb.presentation.components.SearchTextField
import com.l2code.tmdb.resources.Resources

@Composable
fun HomeScreen(navController: NavController = rememberNavController()) {
    val viewModel: HomeViewModel = remember { HomeViewModel() }
    val uiState by viewModel.uiState.collectAsState()

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
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                Header(uiState, viewModel::onSearchTextChanged)
            }
        }
    }
}

@Composable
fun Header(
    uiState: HomeState,
    onSearchTextChanged: (String) -> Unit
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
                modifier = Modifier.size(40.dp).background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center,
            ){
                Box(modifier = Modifier.size(36.dp).background(Color.DarkGray, shape = CircleShape))
            }
        }
        SearchTextField(
            modifier = Modifier.padding(horizontal = 30.dp),
            value = uiState.textSearchField,
            onValueChange = onSearchTextChanged,
        )
    }
}



