package com.example.moviefinder.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.example.moviefinder.databinding.FragmentDetailBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment(
) : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    var url: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
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
        binding.webviewMovie.loadUrl(url)
    }
}