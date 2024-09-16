package com.ead.lib.nomoreadsonmywebviewplayer.models

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.ByteArrayInputStream
import java.net.MalformedURLException
import java.net.URL

/**
 * Blocker client that controls the event
 * to validate the loading of a url
 */
open class BaseClient(
    /**
     * @param *url to validate is called when a url is loading
     */
    var url : String) :  WebViewClient() {


    /**
     * Empty resource for the web view to block the request
     */
    protected val emptyResource : WebResourceResponse =
        WebResourceResponse(
            "text/plain",
            "utf-8",
            ByteArrayInputStream(
                "".toByteArray()
            )
        )


    /**
     * @return domain of the url to intercept and validate the loading
     */
    private val domain : String? get() = run {



        /**
         * check the structure of the url
         */
        val url = try { URL(this.url) } catch (e: MalformedURLException) { null }



        /**
         * and finally return de domain
         */
        url?.host?.split('.')?.firstOrNull { it != "www" } ?: url?.host
    }



    /**
     * @return regex to validate the url
     */
    private val permittedRegex : Regex? get() = run {
        val domain = domain ?: return null
        ".*${domain}.*".toRegex()
    }



    /**
     * Used to validate the overriding of the url
     * in basic way just checking by domain
     * in case its passes the regex
     * call onPassingOverrideUrl
     * to check the next layer of security
     */
    @Deprecated("use OnPassingOverrideUrl Instead", ReplaceWith("false"))
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return if (permittedRegex?.matches(request?.url.toString()) == true)
            false
        else
            onPassingOverrideUrl(view, request)
    }



    /**
     * Used to validate the intercepting of the url
     * in basic way just checking by domain
     * in case its passes the regex
     * call onPassingInterceptRequest
     * to check the next layer of security
     */
    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {


        /**
         * check the structure of the url using the regex
         */
        return if (permittedRegex?.matches(request?.url.toString()) == true)
            super.shouldInterceptRequest(view, request)
        else
            onPassingInterceptRequest(view, request)
    }



    /**
     * Used to validate the overriding of the url on the next layer of security
     */
    protected open fun onPassingOverrideUrl(view: WebView?, request: WebResourceRequest?) : Boolean = true



    /**
     * Used to validate the intercepting of the url on the next layer of security
     */
    protected open fun onPassingInterceptRequest(view: WebView?, request: WebResourceRequest?) : WebResourceResponse? = null
}