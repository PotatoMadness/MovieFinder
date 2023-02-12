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
    private val _movieList = MutableStateFlow<PagingData<MovieItem>>(PagingData.empty())
    val movieList = _movieList.asStateFlow()

    val favoriteList = getFavoriteMovieListUseCase.favoriteList.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _changeFavorite = MutableSharedFlow<Any>()
    val changeFavorite = _changeFavorite.asSharedFlow()

    val query = MutableStateFlow("") // stateflow로 바꿔서 paging에 map.trans 적용 -> movielist

    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                query.debounce(1000).collectLatest {
                    findMovie()
                }
            }
            launch {
                getFavoriteList()
            }
        }
    }

    suspend fun findMovie() { // adapter에서 payload 적용하면 parameter로 전달
        if (query.value.isNullOrEmpty()) _movieList.emit(PagingData.empty())
        else {
            val result = findMovieUseCase.invoke(query.value, viewModelScope)
            _movieList.emit(result.first())
        }
    }

    fun updateFavorite(item: MovieItem, isFavorite: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            val result = updateFavoriteMovieUseCase.invoke(isFavorite, item)
            if (result.isSuccess) {
                _changeFavorite.emit(Any())
            }
        }
    }

    fun getFavoriteList() {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteMovieListUseCase.invoke()
        }
    }
}