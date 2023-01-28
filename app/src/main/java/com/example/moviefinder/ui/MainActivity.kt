package com.example.moviefinder.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.moviefinder.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val movieFinderFragment = MovieFinderFragment()
    private val favoriteListFragment = FavoriteListFragment()
    private val detailFragment = DetailFragment()

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
    }

    override fun onBackPressed() {
        when(viewModel.state.value) {
            is MainViewModel.UIState.MovieFinder -> super.onBackPressed()
            is MainViewModel.UIState.FavoriteList -> viewModel.moveTo(MainViewModel.UIState.MovieFinder)
            is MainViewModel.UIState.DetailMovie -> viewModel.moveTo(MainViewModel.UIState.MovieFinder)
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.state.collectLatest {
                        val fragment = when (it) {
                            is MainViewModel.UIState.MovieFinder -> movieFinderFragment
                            is MainViewModel.UIState.FavoriteList -> favoriteListFragment
                            is MainViewModel.UIState.DetailMovie -> {
                                detailFragment.url = it.url
                                detailFragment
                            }
                        }
                        supportFragmentManager.beginTransaction()
                            .replace(binding.mainFragment.id, fragment)
                            .commit()
                    }
                }
            }
        }
    }
}