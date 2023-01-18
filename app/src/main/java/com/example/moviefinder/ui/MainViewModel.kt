package com.example.moviefinder.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.moviefinder.model.MovieItem
import com.example.moviefinder.usecase.FindMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val findMovieUseCase: FindMovieUseCase
) : ViewModel(){
    private val _movieList = MutableStateFlow<PagingData<MovieItem>?>(null)
    val movieList = _movieList.asStateFlow()

    val query = MutableStateFlow("")

    fun findMovie(query: String) {
        Log.d("asdf", "query : " +query)
        viewModelScope.launch(Dispatchers.IO) {
            val result = findMovieUseCase.invoke(query)
            _movieList.emit(result.firstOrNull())
        }
    }
}