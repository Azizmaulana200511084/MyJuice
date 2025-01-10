package com.aziz.myjuice.ui.screen.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aziz.myjuice.ui.components.AvailableContent
import com.aziz.myjuice.ui.components.EmptyContent
import com.aziz.myjuice.ui.components.ErrorContent
import com.aziz.myjuice.ui.components.LoadingIndicator
import com.aziz.myjuice.dataroom.local.MyJuiceEntity
import com.aziz.myjuice.dataroom.fakedata.UiState

@Composable
fun FavoriteScreen(navController: NavController) {
    val favoriteViewModel = hiltViewModel<FavoriteViewModel>()

    favoriteViewModel.allFavoriteMyJuice.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> LoadingIndicator()
            is UiState.Error -> ErrorContent()
            is UiState.Success -> {
                FavoriteContent(
                    listFavoriteMyJuice = uiState.data,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun FavoriteContent(
    listFavoriteMyJuice: List<MyJuiceEntity>,
    navController: NavController
) {
    when (listFavoriteMyJuice.isEmpty()) {
        true -> EmptyContent()
        false -> AvailableContent(listFavoriteMyJuice, navController)
    }
}