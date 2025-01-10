package com.aziz.myjuice.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.aziz.myjuice.dataroom.local.MyJuiceEntity
import com.aziz.myjuice.ui.TopBar
import com.aziz.myjuice.ui.components.AvailableContent
import com.aziz.myjuice.ui.components.EmptyContent
import com.aziz.myjuice.ui.components.ErrorContent
import com.aziz.myjuice.ui.components.LoadingIndicator
import com.aziz.myjuice.ui.components.SearchBar
import com.aziz.myjuice.ui.navigation.Screen
import com.aziz.myjuice.dataroom.fakedata.UiState

@Composable
fun HomeScreen(navController: NavController) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val homeState by homeViewModel.homeState

    homeViewModel.allMyJuice.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> LoadingIndicator()
            is UiState.Error -> ErrorContent()
            is UiState.Success -> {
                HomeContent(
                    listMyJuice = uiState.data,
                    navController = navController,
                    query = homeState.query,
                    onQueryChange = homeViewModel::onQueryChange
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    listMyJuice: List<MyJuiceEntity>,
    navController: NavController,
    query: String,
    onQueryChange: (String) -> Unit
) {
    Column {
        SearchBar(query = query, onQueryChange = onQueryChange)
        if (listMyJuice.isEmpty()) {
            EmptyContent()
        } else {
            when (navController.currentDestination?.route) {
                Screen.Home.route, Screen.Favorite.route, Screen.Profile.route -> {
                    TopBar(navController = navController as NavHostController, currentRoute = navController.currentDestination?.route ?: "")
                }
            }
            AvailableContent(
                listMyJuice = listMyJuice,
                navController = navController
            )
        }
    }
}
