package com.sukitha.ey.eygiphy.presentation.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.usecase.GiphyUseCases
import com.sukitha.ey.eygiphy.domain.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllGiphyViewModel @Inject constructor(
    private val giphyUseCases: GiphyUseCases
) : ViewModel() {

    private val _favouriteGiphyList = MutableStateFlow<List<Giphy>>(listOf())
    val favouriteGiphyList: StateFlow<List<Giphy>> = _favouriteGiphyList

    private val _giphyList = MutableStateFlow<List<Giphy>>(listOf())
    val giphyList: StateFlow<List<Giphy>> = _giphyList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _showError = MutableStateFlow<Pair<String?, String?>>(Pair(null, null))
    val showError: StateFlow<Pair<String?, String?>> = _showError

    init {
        getFavouriteGiphy()
    }

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
                            _showError.value = Pair("", apiResult.errorMessage)
                        }
                    }
                }
        }
    }

    private fun getFavouriteGiphy() {
        viewModelScope.launch {
            giphyUseCases.getFavouriteGiphy.invoke()
                .flowOn(Dispatchers.IO)
                .collect {
                    _favouriteGiphyList.value = it
                }
        }
    }

    fun insertGiphy(giphy: Giphy) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                giphyUseCases.insertGiphy.invoke(giphy)
                getFavouriteGiphy()
            }
        }
    }

    fun removeGiphy(giphy: Giphy) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                giphyUseCases.removeFavouriteGiphy.invoke(giphy)
                getFavouriteGiphy()
            }
        }
    }
}