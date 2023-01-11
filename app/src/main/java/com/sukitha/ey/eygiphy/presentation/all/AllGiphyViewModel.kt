package com.sukitha.ey.eygiphy.presentation.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.usecase.GiphyUseCases
import com.sukitha.ey.eygiphy.domain.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllGiphyViewModel @Inject constructor(
    private val giphyUseCases: GiphyUseCases
) : ViewModel() {

    private val _giphyList = MutableStateFlow<List<Giphy>>(listOf())
    val giphyList: StateFlow<List<Giphy>> = _giphyList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _showError = MutableStateFlow<String?>("")
    val showError: StateFlow<String?> = _showError

    fun fetchTrendingGiphy() {
        viewModelScope.launch {
            _isLoading.value = true
            giphyUseCases.getTrendingGiphyUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .catch {
                    _isLoading.value = false
                }
                .collect { apiResult ->
                    _isLoading.value = false
                    when (apiResult) {
                        is ApiResult.Success -> {
                            _giphyList.value = apiResult.data ?: emptyList()
                        }
                        is ApiResult.Error -> {}
                    }
                }
        }
    }

    fun fetchGiphy(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            giphyUseCases.getGiphyUseCase.invoke(query)
                .flowOn(Dispatchers.IO)
                .collect { apiResult ->
                    _isLoading.value = false
                    when (apiResult) {
                        is ApiResult.Success -> {
                            _giphyList.value = apiResult.data ?: emptyList()
                        }
                        is ApiResult.Error -> {
                            _showError.value = apiResult.errorMessage
                        }
                    }
                }
        }
    }
}