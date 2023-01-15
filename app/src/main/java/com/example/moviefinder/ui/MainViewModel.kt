package com.example.moviefinder.ui

import androidx.lifecycle.ViewModel
import com.example.moviefinder.usecase.FindMovieUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val findMovieUseCase: FindMovieUseCase
) : ViewModel(){
    init {
        // findMovieUseCase
    }
}