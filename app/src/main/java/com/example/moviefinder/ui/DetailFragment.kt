package com.example.moviefinder.ui

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.databinding.FragmentDetailBinding
import com.example.moviefinder.model.MovieItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment(
) : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var item: MovieItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : DetailFragmentArgs by navArgs()
        item = args.movieItem
        initView()
        initObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initView() {
        with(binding.favItem) {
            Glide.with(requireContext()).load(item.image)
                .into(imgMovieItem)
            tvTitle.text = Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY).toString()
            tvDirector.text = Html.fromHtml(
                String.format(
                    requireContext().getString(R.string.movie_director),
                    item.director
                ), Html.FROM_HTML_MODE_LEGACY).toString()
            tvActors.text = Html.fromHtml(
                String.format(requireContext().getString(R.string.movie_actors), item.actor),
                Html.FROM_HTML_MODE_LEGACY).toString()
            tvRate.text = Html.fromHtml(
                String.format(
                    requireContext().getString(R.string.movie_rate),
                    item.userRating
                ), Html.FROM_HTML_MODE_LEGACY).toString()
            updateFavorite()

            imgFav.setOnClickListener {
                val isFavorite = viewModel.favoriteList.value?.any { it.link == item.link } ?: false
                viewModel.updateFavorite(item, isFavorite)
            }
        }

        val settings =
            binding.webviewMovie.settings
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.supportMultipleWindows()
        settings.javaScriptCanOpenWindowsAutomatically = true

        binding.webviewMovie.webViewClient = WebViewClient()
        binding.webviewMovie.webChromeClient = WebChromeClient()
        binding.webviewMovie.loadUrl(item.link)
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.favoriteList.collect {
                    updateFavorite()
                }
            }
        }
    }

    private fun updateFavorite() {
        val isFavorite = viewModel.favoriteList.value?.any { it.link == item.link } ?: false
        val imgRes = if(isFavorite) R.drawable.icon_star_fill_lg else R.drawable.icon_star_lg
        binding.favItem.imgFav.setImageDrawable(requireContext().getDrawable(imgRes))
    }
}