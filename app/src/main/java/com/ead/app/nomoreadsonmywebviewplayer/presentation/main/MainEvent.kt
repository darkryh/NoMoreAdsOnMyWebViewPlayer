package com.ead.app.nomoreadsonmywebviewplayer.presentation.main

import com.ead.lib.nomoreadsonmywebviewplayer.NoMoreAdsWebView

sealed class MainEvent {

    data class InitializeWebView(val noMoreAdsWebView: NoMoreAdsWebView) : MainEvent()
    data class LoadingUrl(val url: String) : MainEvent()
}
