package com.sukitha.ey.eygiphy.presentation.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.usecase.GiphyUseCases
import com.sukitha.ey.eygiphy.domain.util.ApiResult
import com.sukitha.ey.eygiphy.util.ALL_GIPHY_PAGE_SIZE
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

    private val _favouriteGiphyList = MutableStateFlow<List<Giphy>>(listOf())
    val favouriteGiphyList: StateFlow<List<Giphy>> = _favouriteGiphyList

    var page = 1

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
            giphyUseCases.getTrendingGiphyUseCase.invoke((page * ALL_GIPHY_PAGE_SIZE) - 1)
                .flowOn(Dispatchers.IO)
                .catch {
                    _isLoading.value = false
                }
                .collect { apiResult ->
                    _isLoading.value = false
                    when (apiResult) {
                        is ApiResult.Success -> {
                            page++
                            val combinedList = mutableListOf<Giphy>()
                            combinedList.addAll(_giphyList.value)
                            combinedList.addAll(apiResult.data ?: emptyList())
                            _giphyList.value = combinedList.toList()
                        }
                        is ApiResult.Error -> {
                            _showError.value = Pair("", apiResult.errorMessage)
                        }
                    }
                }
        }
    }

    fun fetchGiphy(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            giphyUseCases.getGiphyUseCase.invoke(query, (page * ALL_GIPHY_PAGE_SIZE) - 1)
                .flowOn(Dispatchers.IO)
                .collect { apiResult ->
                    _isLoading.value = false
                    when (apiResult) {
                        is ApiResult.Success -> {
                            page++
                            val combinedList = mutableListOf<Giphy>()
                            combinedList.addAll(_giphyList.value)
                            combinedList.addAll(apiResult.data ?: emptyList())
                            _giphyList.value = combinedList.toList()
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
            giphyUseCases.insertGiphy.invoke(giphy)
            getFavouriteGiphy()
        }
    }

    fun removeGiphy(giphy: Giphy) {
        viewModelScope.launch {
            giphyUseCases.removeFavouriteGiphy.invoke(giphy)
            getFavouriteGiphy()
        }
    }

    fun resetPaging() {
        page = 1
        _giphyList.value = mutableListOf()
    }
}
