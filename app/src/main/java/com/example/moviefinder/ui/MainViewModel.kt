package com.example.moviefinder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.moviefinder.model.MovieItem
import com.example.moviefinder.usecase.FindMovieUseCase
import com.example.moviefinder.usecase.GetFavoriteMovieListUseCase
import com.example.moviefinder.usecase.UpdateFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val findMovieUseCase: FindMovieUseCase,
    private val updateFavoriteMovieUseCase: UpdateFavoriteMovieUseCase,
    private val getFavoriteMovieListUseCase: GetFavoriteMovieListUseCase
) : ViewModel(){

    sealed class UIState {
        object MovieFinder: UIState()
        object FavoriteList: UIState()
        data class DetailMovie(val url: String): UIState()
    }

    private val _state: MutableStateFlow<UIState> = MutableStateFlow(UIState.MovieFinder)
    val state = _state.asStateFlow()

    private val _movieList = MutableStateFlow<PagingData<MovieItem>>(PagingData.empty())
    val movieList = _movieList.asStateFlow()

    private val _favoriteList = MutableStateFlow<PagingData<MovieItem>>(PagingData.empty())
    val favoriteList = _favoriteList.asStateFlow()

    private val _changeFavorite = MutableSharedFlow<Any>()
    val changeFavorite = _changeFavorite.asSharedFlow()

    val query = MutableStateFlow("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            query.debounce(1000).collectLatest {
                findMovie()
            }
        }
    }

    suspend fun findMovie() {
        if (query.value.isNullOrEmpty()) _movieList.emit(PagingData.empty())
        else {
            val result = findMovieUseCase.invoke(query.value)
            _movieList.emit(result.first())
        }
    }

    fun updateFavorite(item: MovieItem){
        viewModelScope.launch(Dispatchers.IO) {
            val result = updateFavoriteMovieUseCase.invoke(item)
            if (result.isSuccess) {
                _changeFavorite.emit(Any())
            }
        }
    }

    fun moveTo(to: UIState) {
        viewModelScope.launch {
            _state.emit(to)
        }
    }

    fun getFavoriteList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getFavoriteMovieListUseCase.invoke()
            _favoriteList.emit(result.first())
        }
    }
}