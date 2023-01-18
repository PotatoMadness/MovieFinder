package com.example.moviefinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.moviefinder.databinding.FragmentMovieFinderBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class MovieFinderFragment : Fragment() {

    private var _binding: FragmentMovieFinderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private lateinit var movieAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieFinderBinding.inflate(inflater, container, false)
        binding.vm = viewModel
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

    private fun initView() {
        binding.etMovieQuery.onRightDrawableClicked {
            it.text.clear()
        }
        movieAdapter = MovieListAdapter()
        binding.rvMovieList.adapter = movieAdapter
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                launch {
                    viewModel.query.filter { it.isNotEmpty() }.debounce(1000).collectLatest {
                        viewModel.findMovie(query = it)
                    }
                }
                launch {
                    viewModel.movieList.filterNotNull().collect {
                        // 검색결과 처리
                        movieAdapter.submitData(it)
                    }
                }
            }
        }
    }
}

fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
    this.setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is EditText) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}