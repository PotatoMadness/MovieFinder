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
import androidx.navigation.Navigation
import com.example.app.R
import com.example.app.databinding.FragmentFavoriteListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteListFragment : Fragment() {
    private var _binding: FragmentFavoriteListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var favoriteAdapter: FavoriteListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(){
        favoriteAdapter = FavoriteListAdapter (onFavoriteClicked = { item, isFavorite -> // onClick
            viewModel.updateFavorite(item, isFavorite)
        })
        binding.rvFavList.adapter = favoriteAdapter
        binding.btnClose.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_favoriteFragment_to_finderFragment)
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.favoriteList.filterNotNull().collectLatest {
                        favoriteAdapter.favoriteList = it
                        favoriteAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}