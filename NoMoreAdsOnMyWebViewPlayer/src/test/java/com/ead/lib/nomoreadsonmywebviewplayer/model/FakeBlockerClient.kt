package com.ead.lib.nomoreadsonmywebviewplayer.model

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import com.ead.lib.nomoreadsonmywebviewplayer.models.BlockerClient

class FakeBlockerClient(
    url: String?= null,
) : BlockerClient(url) {

    var isOverrideUrlLoadingCalled = false
    var isInterceptRequestCalled = false


    override fun onOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        isOverrideUrlLoadingCalled = true
        return super.onOverrideUrlLoading(view, request)
    }

    override fun onInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        isInterceptRequestCalled = true
        return super.onInterceptRequest(view, request)
    }

}