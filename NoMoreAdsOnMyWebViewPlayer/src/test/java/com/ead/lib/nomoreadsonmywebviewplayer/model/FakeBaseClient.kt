package com.ead.lib.nomoreadsonmywebviewplayer.model

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import com.ead.lib.nomoreadsonmywebviewplayer.models.BaseClient

class FakeBaseClient(
    url: String,
) : BaseClient(url) {

    var isPassingOverrideUrlCalled = false
    var isPassingInterceptRequestCalled = false

    override fun onPassingOverrideUrl(view: WebView?, request: WebResourceRequest?): Boolean {
        isPassingOverrideUrlCalled = true
        return super.onPassingOverrideUrl(view, request)
    }


    @Deprecated(
        "use onPassingInterceptRequest Instead",
        replaceWith = ReplaceWith("onPassingInterceptRequest(view, request)")
    )
    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        return if (onPassingFirstLayerOfSecurity(request,isOverriding = true)) {
            return null
        }
        else {
            onPassingInterceptRequest(view, request)
        }
    }

    override fun onPassingInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
        isPassingInterceptRequestCalled = true
        return super.onPassingInterceptRequest(view, request)
    }

}