package com.ead.app.nomoreadsonmywebviewplayer.presentation.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ead.lib.nomoreadsonmywebviewplayer.NoMoreAdsWebView

class MainViewModel: ViewModel() {

    private val _noMoreAdsWebView : MutableState<NoMoreAdsWebView?> = mutableStateOf(null)
    val noMoreAdsWebView : State<NoMoreAdsWebView?> = _noMoreAdsWebView

    fun onEvent(event: MainEvent) {
        when(event) {
            is MainEvent.InitializeWebView -> {
                _noMoreAdsWebView.value = event.noMoreAdsWebView
            }
            is MainEvent.LoadingUrl -> {
                noMoreAdsWebView.value?.loadUrl(event.url)
            }
        }
    }
}