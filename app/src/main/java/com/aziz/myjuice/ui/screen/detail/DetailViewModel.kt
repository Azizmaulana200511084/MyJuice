package com.aziz.myjuice.ui.screen.detail

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
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _myjuice = MutableStateFlow<UiState<MyJuiceEntity>>(UiState.Loading)
    val myjuice = _myjuice.asStateFlow()

    fun getMyJuice(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMyJuice(id)
                .catch { _myjuice.value = UiState.Error(it.message.toString()) }
                .collect { _myjuice.value = UiState.Success(it) }
        }
    }

    fun updateFavoriteMyJuice(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteMyJuice(id, isFavorite)
        }
    }
}