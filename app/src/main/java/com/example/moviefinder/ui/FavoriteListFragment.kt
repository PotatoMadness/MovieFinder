package com.example.moviefinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import com.example.moviefinder.databinding.FragmentFavoriteListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteListFragment : Fragment() {
    private var _binding: FragmentFavoriteListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var favoriteAdapter: MovieListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteListBinding.inflate(inflater, container, false)

        initView()
        initObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavoriteList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(){
        favoriteAdapter = MovieListAdapter (onFavoriteClicked = { item -> // onClick
            viewModel.updateFavorite(item)
        })
        binding.rvFavList.adapter = favoriteAdapter
        binding.btnClose.setOnClickListener{
            viewModel.moveTo(MainViewModel.UIState.MovieFinder)
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.favoriteList.collectLatest {
                        favoriteAdapter.submitData(it)
                    }
                }
                launch {
                    viewModel.changeFavorite.collectLatest {
                        viewModel.getFavoriteList()
                    }
                }
            }
        }
    }
}