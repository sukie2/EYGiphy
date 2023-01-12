package com.sukitha.ey.eygiphy.presentation.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.usecase.GiphyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouriteGiphyViewModel @Inject constructor(
    private val giphyUseCases: GiphyUseCases
) : ViewModel() {

    private val _favouriteGiphyList = MutableStateFlow<List<Giphy>>(listOf())
    val favouriteGiphyList: StateFlow<List<Giphy>> = _favouriteGiphyList

    init {
        getFavouriteGiphy()
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

    fun removeGiphy(giphy: Giphy) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                giphyUseCases.removeFavouriteGiphy.invoke(giphy)
                getFavouriteGiphy()
            }
        }
    }
}