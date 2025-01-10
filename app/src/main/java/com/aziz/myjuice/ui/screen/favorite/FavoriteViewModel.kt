package com.aziz.myjuice.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aziz.myjuice.dataroom.local.MyJuiceEntity
import com.aziz.myjuice.dataroom.repository.Repository
import com.aziz.myjuice.dataroom.fakedata.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _allFavoriteMyJuice = MutableStateFlow<UiState<List<MyJuiceEntity>>>(UiState.Loading)
    val allFavoriteMyJuice = _allFavoriteMyJuice.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavoriteMyJuice()
                .catch { _allFavoriteMyJuice.value = UiState.Error(it.message.toString()) }
                .collect { _allFavoriteMyJuice.value = UiState.Success(it) }
        }
    }

}