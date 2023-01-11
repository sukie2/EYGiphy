package com.sukitha.ey.eygiphy.presentation.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukitha.ey.eygiphy.data.remote.data.GiphyDto
import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.usecase.GiphyUseCases
import com.sukitha.ey.eygiphy.domain.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllGiphyViewModel @Inject constructor(
    private val giphyUseCases: GiphyUseCases
) : ViewModel() {

    private val _giphyList = MutableStateFlow<List<Giphy>>(listOf())
    val giphyList: StateFlow<List<Giphy>> = _giphyList

    fun fetchTrendingGiphy() {
        viewModelScope.launch {
            giphyUseCases.getTrendingGiphy.invoke()
                .flowOn(Dispatchers.IO)
                .catch {
                    val x = 0
                }
                .collect{ apiResult ->
                    when (apiResult) {
                        is ApiResult.Success -> {
                            _giphyList.value = apiResult.data ?: emptyList()
                        }
                        is ApiResult.Error -> {}
                    }
                }
        }
    }
}