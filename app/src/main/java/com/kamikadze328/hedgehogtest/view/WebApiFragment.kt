package com.kamikadze328.hedgehogtest.view

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.kamikadze328.hedgehogtest.R
import com.kamikadze328.hedgehogtest.databinding.FragmentWebApiBinding
import kotlinx.parcelize.Parcelize


class WebApiFragment : Fragment(R.layout.fragment_web_api) {

    companion object {
        const val STATE_ARG = "state"
    }

    private var _binding: FragmentWebApiBinding? = null
    private val binding get() = _binding!!

    private var state = WebApiState.DEFAULT
        set(value) {
            if (field != value) onChangeState(value)
            field = value
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWebApiBinding.bind(view)

        if (savedInstanceState != null) {
            state = savedInstanceState.getParcelable(STATE_ARG) ?: WebApiState.DEFAULT
            if (state == WebApiState.READY) binding.webviewApi.restoreState(savedInstanceState)
        }
        if (state == WebApiState.DEFAULT) setupWebView()
        if (state != WebApiState.READY) startLoading()

    }

    private fun setupWebView() {
        binding.webviewApi.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                state = WebApiState.LOADING
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                state = WebApiState.READY
            }
        }
        binding.webviewApi.settings.javaScriptEnabled = true
        binding.webviewApi.settings.domStorageEnabled = true
    }

    private fun onPageLoaded() {
        binding.progressbarApi.visibility = View.GONE
        binding.webviewApi.visibility = View.VISIBLE
    }

    private fun onPageStarted() {
        binding.webviewApi.visibility = View.INVISIBLE
        binding.progressbarApi.visibility = View.VISIBLE
    }

    private fun onChangeState(state: WebApiState) {
        when (state) {
            WebApiState.LOADING -> onPageStarted()
            WebApiState.READY -> onPageLoaded()
            else -> {
            }
        }
    }

    private fun startLoading() {
        binding.webviewApi.loadUrl("https://www.icndb.com/api/")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(STATE_ARG, state)
        binding.webviewApi.saveState(outState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}

@Parcelize
enum class WebApiState : Parcelable {
    DEFAULT,
    LOADING,
    READY
}


