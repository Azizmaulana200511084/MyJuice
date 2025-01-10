package com.aziz.myjuice.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aziz.myjuice.dataroom.local.MyJuiceEntity
import com.aziz.myjuice.dataroom.repository.Repository
import com.aziz.myjuice.dataroom.fakedata.MyJuiceData
import com.aziz.myjuice.dataroom.fakedata.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _allMyJuice = MutableStateFlow<UiState<List<MyJuiceEntity>>>(UiState.Loading)
    val allMyJuice = _allMyJuice.asStateFlow()

    private val _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllMyJuice().collect { myjuice ->
                when (myjuice.isEmpty()) {
                    true -> repository.insertAllMyJuice(MyJuiceData.dummy)
                    else -> _allMyJuice.value = UiState.Success(myjuice)
                }
            }
        }
    }

    private fun searchMyJuice(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchMyJuice(query)
                .catch { _allMyJuice.value = UiState.Error(it.message.toString()) }
                .collect { _allMyJuice.value = UiState.Success(it) }
        }
    }

    fun onQueryChange(query: String) {
        _homeState.value = _homeState.value.copy(query = query)
        searchMyJuice(query)
    }
}